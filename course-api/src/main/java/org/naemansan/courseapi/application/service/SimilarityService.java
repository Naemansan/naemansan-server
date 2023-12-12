package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarEnrolledCoursesCommand;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarPersonalCoursesCommand;
import org.naemansan.courseapi.application.port.in.usecase.SimilarityUseCase;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.application.port.out.TagServicePort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;
import org.naemansan.courseapi.dto.common.TagDto;
import org.naemansan.courseapi.dto.response.CourseSimilarityDto;
import org.naemansan.courseapi.dto.type.EState;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimilarityService implements SimilarityUseCase {
    private final CourseRepositoryPort courseRepositoryPort;

    private final TagServicePort tagServicePort;

    @Override
    public List<CourseSimilarityDto> findSimilarPersonalCourses(ReadSimilarPersonalCoursesCommand command) {
        System.out.println("findSimilarPersonalCourses");

        // 유사도 검사 로직
        List<Course> nearCourses = courseRepositoryPort.findNearCoursesByUserIdAndLocationAndState(
                UUID.fromString(command.getUserId()),
                CourseUtil.location2Point(command.getLocations().get(0)),
                EState.PERSONAL);

        List<Long> nearCourseIds = CourseUtil.analyzeSimilarity(
                command.getLocations(),
                nearCourses);

        // 유사도 검사 결과를 바탕으로 코스 정보를 가져옴
        Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(nearCourseIds);

        // 네트워크 통신량을 줄이기 위해 중복되는 태그를 제거하기 위해 Set으로 변환
        Set<Long> tagIds = courses.values().stream()
                .flatMap(course -> course.getTags().stream())
                .map(CourseTag::getTagId)
                .collect(Collectors.toSet());

        Map<Long, String> tagNames = tagServicePort.findByTagIds(tagIds.stream().toList());

        // 반환
        return courses.values().stream()
                .map(course -> CourseSimilarityDto.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .startLocationName(course.getStartLocationName())
                        .distance(String.valueOf(Math.round(course.getDistance())))
                        .tags(course.getTags().stream()
                                .map(courseTag -> TagDto.builder()
                                        .id(courseTag.getTagId())
                                        .name(tagNames.get(courseTag.getTagId()))
                                        .build())
                                .toList()).build())
                .toList();
    }

    @Override
    public List<CourseSimilarityDto> findSimilarEnrolledCourses(ReadSimilarEnrolledCoursesCommand command) {
        System.out.println("findSimilarEnrolledCourses");

        // 유사도 검사 로직
        List<Course> nearCourses = courseRepositoryPort.findNearCoursesByUserIdAndLocationAndState(
                null,
                CourseUtil.location2Point(command.getLocations().get(0)),
                EState.ENROLLED);

        List<Long> nearCourseIds = CourseUtil.analyzeSimilarity(
                command.getLocations(),
                nearCourses);

        // 유사도 검사 결과를 바탕으로 코스 정보를 가져옴
        Map<Long, Course> courses = courseRepositoryPort.findCoursesByIds(nearCourseIds);

        // 네트워크 통신량을 줄이기 위해 중복되는 태그를 제거하기 위해 Set으로 변환
        Set<Long> tagIds = courses.values().stream()
                .flatMap(course -> course.getTags().stream())
                .map(CourseTag::getTagId)
                .collect(Collectors.toSet());

        Map<Long, String> tagNames = tagServicePort.findByTagIds(tagIds.stream().toList());

        return courses.values().stream()
                .map(course -> CourseSimilarityDto.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .startLocationName(course.getStartLocationName())
                        .distance(String.valueOf(Math.round(course.getDistance())))
                        .tags(course.getTags().stream()
                                .map(courseTag -> TagDto.builder()
                                        .id(courseTag.getTagId())
                                        .name(tagNames.get(courseTag.getTagId()))
                                        .build())
                                .toList()).build())
                .toList();
    }
}
