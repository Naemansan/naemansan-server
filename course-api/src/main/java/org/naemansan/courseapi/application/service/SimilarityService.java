package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarEnrolledCoursesCommand;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarPersonalCoursesCommand;
import org.naemansan.courseapi.application.port.in.usecase.SimilarityUseCase;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.application.port.out.TagServicePort;
import org.naemansan.courseapi.application.port.out.UserServicePort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;
import org.naemansan.courseapi.dto.persistent.UserNamePersistent;
import org.naemansan.courseapi.dto.response.CourseSimilarityDto;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimilarityService implements SimilarityUseCase {
    private final CourseRepositoryPort courseRepositoryPort;

    private final TagServicePort tagServicePort;

    @Override
    public List<CourseSimilarityDto> findSimilarPersonalCourses(ReadSimilarPersonalCoursesCommand command) {
        // 유사도 검사 로직 필요(산책로를 생성할 때는 자신의 것만 비교하면 됨)
        List<Course> nearCourses = courseRepositoryPort.findNearCoursesByUserIdAndLocationAndIsEnrolled(
                UUID.fromString(command.getUserId()),
                CourseUtil.location2Point(command.getLocations().get(0)),
                false);

        List<Long> nearCourseIds = CourseUtil.analyzeSimilarity(
                command.getLocations(),
                nearCourses);

        Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(nearCourseIds);

        return courses.values().stream()
                .map(course -> CourseSimilarityDto.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .startLocationName(course.getStartLocationName())
                        .distance(course.getDistance())
                        .tags(course.getTags().isEmpty() ?
                                List.of() :
                                tagServicePort.findByTagIds(
                                course.getTags().stream().map(CourseTag::getTagId).toList()))
                        .build())
                .toList();
    }

    @Override
    public List<CourseSimilarityDto> findSimilarEnrolledCourses(ReadSimilarEnrolledCoursesCommand command) {
        List<Course> nearCourses = courseRepositoryPort.findNearCoursesByUserIdAndLocationAndIsEnrolled(
                null,
                CourseUtil.location2Point(command.getLocations().get(0)),
                true);

        List<Long> nearCourseIds = CourseUtil.analyzeSimilarity(
                command.getLocations(),
                nearCourses);

        Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(nearCourseIds);

        return courses.values().stream()
                .map(course -> CourseSimilarityDto.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .startLocationName(course.getStartLocationName())
                        .distance(course.getDistance())
                        .tags(course.getTags().isEmpty() ?
                                List.of() :
                                tagServicePort.findByTagIds(
                                        course.getTags().stream().map(CourseTag::getTagId).toList()))
                        .build())
                .toList();
    }
}
