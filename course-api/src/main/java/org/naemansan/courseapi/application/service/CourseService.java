package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.naemansan.common.dto.response.PageInfo;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.adapter.out.repository.CourseRepository;
import org.naemansan.courseapi.application.port.in.command.CreateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseStatusCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCoursesCommand;
import org.naemansan.courseapi.application.port.in.usecase.CourseUseCase;
import org.naemansan.courseapi.application.port.out.*;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;
import org.naemansan.courseapi.domain.Spot;
import org.naemansan.courseapi.dto.common.TagDto;
import org.naemansan.courseapi.dto.persistent.UserNamePersistent;
import org.naemansan.courseapi.dto.persistent.SpotPersistent;
import org.naemansan.courseapi.dto.request.SpotDto;
import org.naemansan.courseapi.dto.response.CourseDetailDto;
import org.naemansan.courseapi.dto.response.CourseListDto;
import org.naemansan.courseapi.dto.type.EState;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements CourseUseCase {
    private final TagServicePort tagServicePort;
    private final UserServicePort userServicePort;
    private final ImageServicePort imageServicePort;
    private final NaverMapServicePort naverMapServicePort;

    private final CourseRepositoryPort courseRepositoryPort;
    private final CourseTagRepositoryPort courseTagRepositoryPort;
    private final SpotRepositoryPort spotRepositoryPort;
    private final LikeRepositoryPort likeRepositoryPort;
    private final MomentRepositoryPort momentRepositoryPort;

    @Override
    @Transactional
    public Map<String, Object> createCourse(CreateCourseCommand command) {
        // User 확인(필터 단에서 하기 때문에 필요 없음)
        // TagID 검증 로직


        // Course 생성
        Course course = courseRepositoryPort.craeteCourse(
                command.getTitle(),
                command.getContent(),
                naverMapServicePort.getLocationName(command.getLocations().get(0)),
                CourseUtil.locations2MultiPoint(command.getLocations()),
                CourseUtil.calculateDistanceSum(command.getLocations()),
                UUID.fromString(command.getUserId()));


        // CourseTag 생성
        courseTagRepositoryPort.createCourseTags(command.getTagIds(), course);

        // Image 저장
        List<String> preSignedUrls = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        for (SpotDto spot : command.getSpots()) {
            if (spot.imageState().isUsed()) {
                Map<String, String> imageUrlsMap = imageServicePort.getUploadImageUrl(spot.imageState().type());
                preSignedUrls.add(imageUrlsMap.get("preSignedUrl"));
                imageUrls.add(imageUrlsMap.get("fileUrl"));
            } else {
                preSignedUrls.add(null);
                imageUrls.add(null);
            }
        }

        // CourseSpot 생성
        spotRepositoryPort.saveAll(
                command.getSpots().stream()
                        .map(spot -> SpotPersistent.fromDto(
                                spot,
                                imageUrls.get(command.getSpots().indexOf(spot)),
                                CourseUtil.location2Point(spot.location())))
                        .toList(),
                course
        );

        // 반환
        return Map.of(
                "preSignedUrls", preSignedUrls
        );
    }

    @Override
    public CourseDetailDto findCourseById(ReadCourseCommand command) {
        // Course 조회
        Course course = courseRepositoryPort.findCourseById(command.getId());

        // 작성자 조회
        UserNamePersistent userNamePersistent = userServicePort.findUserName(
                course.getUserId().toString());

        // tagName 조회
        Map<Long, String> tagNames = tagServicePort.findByTagIds(
                course.getTags().stream().map(CourseTag::getTagId).toList());

        Long likeCount = likeRepositoryPort.countByCourse(course);

        return CourseDetailDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .startLocationName(course.getStartLocationName())
                .locations(CourseUtil.multiPoint2Locations(course.getLocations()))
                .tags(course.getTags().stream()
                        .map(courseTag -> TagDto.builder()
                                .id(courseTag.getTagId())
                                .name(tagNames.get(courseTag.getTagId()))
                                .build())
                        .toList())
                .distance(String.valueOf(Math.round(course.getDistance())))
                .createdAt(course.getCreatedAt().toString())
                .userId(userNamePersistent.id())
                .userNickName(userNamePersistent.nickname())
                .userProfileImageUrl(userNamePersistent.profileImageUrl())
                .likeCount(likeCount)
                .build();
    }

    @Override
    public Map<String, Object> findCourses(ReadCoursesCommand command) {
        // Pageable 생성
        Pageable pageable = PageRequest.of(
                command.getPage(),
                command.getSize(),
                Sort.by("createdAt").descending());

        // tagIds 존재에 따라 분기
        if (command.getTagIds() == null) {
            // Course 조회
            Page<Course> courses = courseRepositoryPort.findCourses(pageable);

            // TagNames 조회
            Set<Long> tagIds = courses.getContent().stream()
                    .flatMap(course -> course.getTags().stream())
                    .map(CourseTag::getTagId)
                    .collect(Collectors.toSet());

            Map<Long, String> tagNames = tagServicePort.findByTagIds(tagIds.stream().toList());

            // Like, Moment Count 조회
            Map<Long, Long> likeCounts = likeRepositoryPort.countByCourses(courses.getContent());
            Map<Long, Long> momentCounts = momentRepositoryPort.countByCourses(courses.getContent());

            // 반환
            return Map.of(
                    "courses", courses.stream()
                            .map(course -> CourseListDto.builder()
                                    .id(course.getId())
                                    .title(course.getTitle())
                                    .startLocationName(course.getStartLocationName())
                                    .distance(String.valueOf(Math.round(course.getDistance())))
                                    .locations(CourseUtil.multiPoint2Locations(course.getLocations()))
                                    .tags(course.getTags().stream()
                                            .map(courseTag -> TagDto.builder()
                                                    .id(courseTag.getTagId())
                                                    .name(tagNames.get(courseTag.getTagId()))
                                                    .build())
                                            .toList())
                                    .momentCount(momentCounts.getOrDefault(course.getId(), 0L))
                                    .likeCount(likeCounts.getOrDefault(course.getId(), 0L))
                                    .build())
                            .toList(),
                    "pageInfo", PageInfo.fromPage(courses)
            );
        } else {
            // CourseId 조회
            Page<CourseRepository.DateForm> dataForms = courseRepositoryPort.findCoursesByTagIds(command.getTagIds(), pageable);

            // Course 조회
            Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(
                    dataForms.getContent().stream().map(CourseRepository.DateForm::getId).toList());

            // TagNames 조회
            Set<Long> tagIds = courses.values().stream()
                    .flatMap(course -> course.getTags().stream())
                    .map(CourseTag::getTagId)
                    .collect(Collectors.toSet());

            Map<Long, String> tagNames = tagServicePort.findByTagIds(tagIds.stream().toList());

            // Like, Moment Count 조회
            Map<Long, Long> likeCounts = likeRepositoryPort.countByCourses(courses.values().stream().toList());
            Map<Long, Long> momentCounts = momentRepositoryPort.countByCourses(courses.values().stream().toList());

            // 반환
            return Map.of(
                    "courses", dataForms.stream()
                            .map(dataForm -> CourseListDto.builder()
                                    .id(dataForm.getId())
                                    .title(courses.get(dataForm.getId()).getTitle())
                                    .startLocationName(courses.get(dataForm.getId()).getStartLocationName())
                                    .distance(String.valueOf(Math.round(courses.get(dataForm.getId()).getDistance())))
                                    .locations(CourseUtil.multiPoint2Locations(courses.get(dataForm.getId()).getLocations()))
                                    .tags(courses.get(dataForm.getId()).getTags().stream()
                                            .map(courseTag -> TagDto.builder()
                                                    .id(courseTag.getTagId())
                                                    .name(tagNames.get(courseTag.getTagId()))
                                                    .build())
                                            .toList())
                                    .momentCount(momentCounts.getOrDefault(dataForm.getId(), 0L))
                                    .likeCount(likeCounts.getOrDefault(dataForm.getId(), 0L))
                                    .build())
                            .toList(),
                    "pageInfo", PageInfo.fromPage(dataForms)
            );
        }
    }

    @Override
    public Map<String, Object> findCoursesByLocation(ReadCoursesCommand command) {
        // Pageable 생성
        Pageable pageable = PageRequest.of(
                command.getPage(),
                command.getSize(),
                Sort.by("radius"));

        Point location = CourseUtil.location2Point(command.getLocation());
        Page<CourseRepository.RadiusForm> locationForms = null;

        // tagIds 존재에 따라 분기 / Course Ids 조회
        if (command.getTagIds() == null) {
            locationForms = courseRepositoryPort.findCoursesByLocation(location, pageable);
        } else {
            locationForms = courseRepositoryPort.findCoursesByTagIdsAndLocation(command.getTagIds(), location, pageable);
        }

        // Course 조회
        Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(
                locationForms.getContent().stream().map(CourseRepository.RadiusForm::getId).toList());

        // TagNames 조회
        Set<Long> tagIds = courses.values().stream()
                .flatMap(course -> course.getTags().stream())
                .map(CourseTag::getTagId)
                .collect(Collectors.toSet());

        Map<Long, String> tagNames = tagServicePort.findByTagIds(tagIds.stream().toList());

        // Like, Moment Count 조회
        Map<Long, Long> likeCounts = likeRepositoryPort.countByCourses(courses.values().stream().toList());
        Map<Long, Long> momentCounts = momentRepositoryPort.countByCourses(courses.values().stream().toList());

        // 반환
        return Map.of(
                "courses", locationForms.stream()
                        .map(radiusForm -> CourseListDto.builder()
                                .id(radiusForm.getId())
                                .title(courses.get(radiusForm.getId()).getTitle())
                                .startLocationName(courses.get(radiusForm.getId()).getStartLocationName())
                                .distance(String.valueOf(Math.round(courses.get(radiusForm.getId()).getDistance())))
                                .locations(CourseUtil.multiPoint2Locations(courses.get(radiusForm.getId()).getLocations()))
                                .tags(courses.get(radiusForm.getId()).getTags().stream()
                                        .map(courseTag -> TagDto.builder()
                                                .id(courseTag.getTagId())
                                                .name(tagNames.get(courseTag.getTagId()))
                                                .build())
                                        .toList())
                                .momentCount(momentCounts.getOrDefault(radiusForm.getId(), 0L))
                                .likeCount(likeCounts.getOrDefault(radiusForm.getId(), 0L))
                                .build())
                        .toList(),
                "pageInfo", PageInfo.fromPage(locationForms)
        );
    }

    @Override
    @Transactional
    public void updateCourse(UpdateCourseCommand command) {
        // User 확인
        UserNamePersistent userNamePersistent = userServicePort.findUserName(command.getUserId());

        // Course
        Course course = courseRepositoryPort.findCourseByIdAndUserId(command.getCourseId(), UUID.fromString(command.getUserId()));

        // 권한 확인
        if (!Objects.equals(userNamePersistent.id(), course.getUserId().toString())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // Spot 조회
        List<Spot> spots = spotRepositoryPort.findByCourse(course);

        // Delete
        courseTagRepositoryPort.deleteCourseTags(course.getTags());
        spotRepositoryPort.deleteAll(spots);

        // Course Update
        course.update(command.getTitle(), command.getContent());

        // CourseTag 생성
        courseTagRepositoryPort.createCourseTags(command.getTagIds(), course);

        // Image 저장


        // CourseSpot 생성
        spotRepositoryPort.saveAll(
                command.getSpots().stream()
                        .map(spot -> SpotPersistent.fromDto(
                                spot,
                                "testtest",
                                CourseUtil.location2Point(spot.location())))
                        .toList(),
                course
        );
    }

    @Override
    @Transactional
    public void updateCourseStatus(UpdateCourseStatusCommand command) {
        // User 확인(필터 단에서 하기 때문에 필요 없음)
        // Course 조회(해당 유저가 작성한 코스인지 확인까지)
        Course course = courseRepositoryPort.findCourseByIdAndUserId(command.getCourseId(), UUID.fromString(command.getUserId()));

        // Update
        course.updateStatus(command.getIsEnrolled() ? EState.ENROLLED : EState.PERSONAL);
    }

    @Override
    @Transactional
    public void deleteCourse(DeleteCourseCommand command) {
        // User 확인(필터 단에서 하기 때문에 필요 없음)
        // Course 조회(해당 유저가 작성한 코스인지 확인까지)
        Course course = courseRepositoryPort.findCourseByIdAndUserId(command.getCourseId(), UUID.fromString(command.getUserId()));

        // Delete
        courseRepositoryPort.deleteCourse(course);
    }
}
