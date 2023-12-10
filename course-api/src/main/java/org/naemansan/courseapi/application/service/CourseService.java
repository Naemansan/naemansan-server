package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.naemansan.common.dto.response.PageInfo;
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
import org.naemansan.courseapi.dto.common.UserNameDto;
import org.naemansan.courseapi.dto.persistent.SpotPersistent;
import org.naemansan.courseapi.dto.response.CourseDetailDto;
import org.naemansan.courseapi.dto.response.CourseListDto;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService implements CourseUseCase {
    private final CourseUtil courseUtil;

    private final UserServicePort userServicePort;
    private final NaverMapServicePort naverMapServicePort;
    private final TagServicePort tagServicePort;

    private final CourseRepositoryPort courseRepositoryPort;
    private final CourseTagRepositoryPort courseTagRepositoryPort;
    private final SpotRepositoryPort spotRepositoryPort;
    private final LikeRepositoryPort likeRepositoryPort;
    private final MomentRepositoryPort momentRepositoryPort;

    @Override
    @Transactional
    public CourseDetailDto createCourse(CreateCourseCommand command) {
        // User 확인

        // Course 생성
        Course course = courseRepositoryPort.craeteCourse(
                command.getTitle(),
                command.getContent(),
                naverMapServicePort.getLocationName(command.getLocations().get(0)),
                courseUtil.locations2MultiPoint(command.getLocations()),
                courseUtil.calculateDistanceSum(command.getLocations()),
                UUID.fromString(command.getUserId()));

        // CourseTag 생성(해당하는 TagID가 유효한지 검증 로직 구현 안됨)
        courseTagRepositoryPort.createCourseTags(command.getTagIds(), course);

        // CourseSpot 생성(이미지 처리 로직 구현 안됨)
        spotRepositoryPort.saveAll(
                command.getSpots().stream()
                        .map(spot -> SpotPersistent.fromDto(
                                spot,
                                "testtest",
                                courseUtil.location2Point(spot.location())))
                        .toList(),
                course
        );

        UserNameDto userNameDto = userServicePort.findUserName(course.getUserId().toString());

        // 리턴
        return CourseDetailDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .startLocationName(course.getStartLocationName())
                .locations(courseUtil.multiPoint2Locations(course.getLocations()))
                .tags(tagServicePort.findByTagIds(command.getTagIds()))
                .distance(String.valueOf(Math.round(course.getDistance())))
                .createdAt(course.getCreatedAt())
                .userId(userNameDto.uuid())
                .userNickName(userNameDto.nickname())
                .userProfileImageUrl(userNameDto.profileImageUrl())
                .build();
    }

    @Override
    public Map<String, Object> findCourses(ReadCoursesCommand command) {
        Pageable pageable = PageRequest.of(
                command.getPage(),
                command.getSize(),
                Sort.by("createdAt").descending());

        if (command.getTagIds() == null) {
            Page<Course> courses = courseRepositoryPort.findCourses(pageable);

            Map<Long, Long> likeCounts = likeRepositoryPort.countByCourses(courses.getContent());
            Map<Long, Long> momentCounts = momentRepositoryPort.countByCourses(courses.getContent());

            return Map.of(
                    "courses", courses.stream()
                            .map(course -> CourseListDto.builder()
                                    .id(course.getId())
                                    .title(course.getTitle())
                                    .startLocationName(course.getStartLocationName())
                                    .distance(String.valueOf(Math.round(course.getDistance())))
                                    .tags(tagServicePort.findByTagIds(
                                            course.getTags().stream().map(CourseTag::getTagId).toList()))
                                    .momentCount(momentCounts.getOrDefault(course.getId(), 0L))
                                    .likeCount(likeCounts.getOrDefault(course.getId(), 0L))
                                    .build())
                            .toList(),
                    "pageInfo", PageInfo.fromPage(courses)
            );
        } else {
            Page<CourseRepository.DateForm> dataForms = courseRepositoryPort.findCoursesByTagIds(command.getTagIds(), pageable);

            Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(
                    dataForms.getContent().stream().map(CourseRepository.DateForm::getId).toList());
            Map<Long, Long> likeCounts = likeRepositoryPort.countByCourses(courses.values().stream().toList());
            Map<Long, Long> momentCounts = momentRepositoryPort.countByCourses(courses.values().stream().toList());

            return Map.of(
                    "courses", dataForms.stream()
                            .map(dataForm -> CourseListDto.builder()
                                    .id(dataForm.getId())
                                    .title(courses.get(dataForm.getId()).getTitle())
                                    .startLocationName(courses.get(dataForm.getId()).getStartLocationName())
                                    .distance(String.valueOf(Math.round(courses.get(dataForm.getId()).getDistance())))
                                    .tags(tagServicePort.findByTagIds(
                                            courses.get(dataForm.getId()).getTags().stream().map(CourseTag::getTagId).toList()))
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
        Point location = courseUtil.location2Point(command.getLocation());
        Pageable pageable = PageRequest.of(
                command.getPage(),
                command.getSize(),
                Sort.by("radius"));

        Page<CourseRepository.LocationForm> locationForms = null;

        if (command.getTagIds() == null) {
            locationForms = courseRepositoryPort.findCoursesByLocation(location, pageable);
        } else {
            locationForms = courseRepositoryPort.findCoursesByTagIdsAndLocation(command.getTagIds(), location, pageable);
        }

        Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(
                locationForms.getContent().stream().map(CourseRepository.LocationForm::getId).toList());

        Map<Long, Long> likeCounts = likeRepositoryPort.countByCourses(courses.values().stream().toList());
        Map<Long, Long> momentCounts = momentRepositoryPort.countByCourses(courses.values().stream().toList());

        return Map.of(
                "courses", locationForms.stream()
                        .map(locationForm -> CourseListDto.builder()
                                .id(locationForm.getId())
                                .title(courses.get(locationForm.getId()).getTitle())
                                .startLocationName(courses.get(locationForm.getId()).getStartLocationName())
                                .distance(String.valueOf(Math.round(locationForm.getRadius())))
                                .tags(tagServicePort.findByTagIds(
                                        courses.get(locationForm.getId()).getTags().stream().map(CourseTag::getTagId).toList()))
                                .momentCount(momentCounts.getOrDefault(locationForm.getId(), 0L))
                                .likeCount(likeCounts.getOrDefault(locationForm.getId(), 0L))
                                .build())
                        .toList(),
                "pageInfo", PageInfo.fromPage(locationForms)
        );
    }

    @Override
    public CourseDetailDto findCourseById(ReadCourseCommand command) {
        // User 확인

        // Course 조회
        Course course = courseRepositoryPort.findCourseById(command.getId());

        // tagName 조회
        List<String> tagNames = tagServicePort.findByTagIds(
                course.getTags().stream().map(CourseTag::getTagId).toList());

        // userName 조회
        UserNameDto userNameDto = userServicePort.findUserName(
                course.getUserId().toString());

        return CourseDetailDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .startLocationName(course.getStartLocationName())
                .locations(courseUtil.multiPoint2Locations(course.getLocations()))
                .tags(tagNames)
                .distance(String.valueOf(Math.round(course.getDistance())))
                .createdAt(course.getCreatedAt())
                .userId(userNameDto.uuid())
                .userNickName(userNameDto.nickname())
                .userProfileImageUrl(userNameDto.profileImageUrl())
                .build();
    }

    @Override
    @Transactional
    public void updateCourse(UpdateCourseCommand command) {
        // User 확인

        // Course, Spot 조회
        Course course = courseRepositoryPort.findCourseByIdAndUserId(command.getCourseId(), UUID.fromString(command.getUserId()));
        List<Spot> spots = spotRepositoryPort.findByCourse(course);

        // Delete
        courseTagRepositoryPort.deleteCourseTags(course.getTags());
        spotRepositoryPort.deleteAll(spots);

        // Update
        course.update(command.getTitle(), command.getContent());
        courseTagRepositoryPort.createCourseTags(command.getTagIds(), course);
        spotRepositoryPort.saveAll(
                command.getSpots().stream()
                        .map(spot -> SpotPersistent.fromDto(
                                spot,
                                "testtest",
                                courseUtil.location2Point(spot.location())))
                        .toList(),
                course
        );
    }

    @Override
    @Transactional
    public void updateCourseStatus(UpdateCourseStatusCommand command) {
        Course course = courseRepositoryPort.findCourseByIdAndUserId(command.getCourseId(), UUID.fromString(command.getUserId()));

        course.updateStatus(command.getIsEnrolled());
    }

    @Override
    @Transactional
    public void deleteCourse(DeleteCourseCommand command) {
        Course course = courseRepositoryPort.findCourseByIdAndUserId(command.getCourseId(), UUID.fromString(command.getUserId()));

        courseRepositoryPort.deleteCourse(course);
    }
}
