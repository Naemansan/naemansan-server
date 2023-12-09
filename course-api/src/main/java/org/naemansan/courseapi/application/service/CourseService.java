package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
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
import org.naemansan.courseapi.dto.common.UserNameDto;
import org.naemansan.courseapi.dto.persistent.SpotPersistent;
import org.naemansan.courseapi.dto.response.CourseDetailDto;
import org.naemansan.courseapi.dto.response.CourseListDto;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService implements CourseUseCase {
    private final CourseUtil courseUtil;

    private final NaverMapServicePort naverMapServicePort;
    private final CourseTagServicePort courseTagServicePort;
    private final UserServicePort userServicePort;

    private final CourseRepositoryPort courseRepositoryPort;
    private final SpotRepositoryPort spotRepositoryPort;

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
        courseRepositoryPort.createCourseTags(command.getTagIds(), course);

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
                .tags(courseTagServicePort.findByTagIds(command.getTagIds()))
                .distance(String.valueOf(Math.round(course.getDistance())))
                .createdAt(course.getCreatedAt())
                .userId(userNameDto.uuid())
                .userNickName(userNameDto.nickname())
                .userProfileImageUrl(userNameDto.profileImageUrl())
                .build();
    }

    @Override
    public List<CourseListDto> findCourses(ReadCoursesCommand command) {
        return null;
    }

    @Override
    public CourseDetailDto findCourseById(ReadCourseCommand command) {
        Course course = courseRepositoryPort.findCourseById(command.getId());

        List<Long> tagIds = course.getTags().stream().map(CourseTag::getTagId).toList();

        UserNameDto userNameDto = userServicePort.findUserName(course.getUserId().toString());

        return CourseDetailDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .startLocationName(course.getStartLocationName())
                .locations(courseUtil.multiPoint2Locations(course.getLocations()))
                .tags(courseTagServicePort.findByTagIds(tagIds))
                .distance(String.valueOf(Math.round(course.getDistance())))
                .createdAt(course.getCreatedAt())
                .userId(userNameDto.uuid())
                .userNickName(userNameDto.nickname())
                .userProfileImageUrl(userNameDto.profileImageUrl())
                .build();
    }

    @Override
    public void updateCourse(UpdateCourseCommand command) {

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
