package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiPoint;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.dto.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.adapter.out.repository.CourseRepository;
import org.naemansan.courseapi.adapter.out.repository.CourseTagRepository;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class CoursePersistentAdapter implements CourseRepositoryPort {
    private final CourseRepository courseRepository;
    private final CourseTagRepository courseTagRepository;

    @Override
    public Course craeteCourse(String title, String content, String startLocationName, MultiPoint locations, Double distance, UUID createdUserId) {
        return courseRepository.save(
                Course.builder()
                        .title(title)
                        .content(content)
                        .startLocationName(startLocationName)
                        .locations(locations)
                        .distance(distance)
                        .userId(createdUserId)
                        .build());
    }

    @Override
    public List<CourseTag> createCourseTags(List<Long> tagIds, Course course) {
        return courseTagRepository.saveAll(tagIds.stream()
                .map(tagId -> new CourseTag(tagId, course))
                .toList());
    }

    @Override
    public void deleteCourseTags(List<CourseTag> courseTags) {
        courseTagRepository.deleteAll(courseTags);
        courseTagRepository.flush();
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE)
        );
    }

    @Override
    public Course findCourseByIdAndUserId(Long id, UUID userId) {
        return courseRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE)
        );
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
}
