package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.adapter.out.repository.CourseRepository;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.dto.type.EState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class CoursePersistentAdapter implements CourseRepositoryPort {
    private final CourseRepository courseRepository;


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
    public Page<Course> findCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Map<Long, Course> findCoursesByIds(List<Long> ids) {
        List<Course> courses = courseRepository.findByIdIn(ids);

        return courses.stream().collect(
                Collectors.toMap(Course::getId, course -> course)
        );
    }

    @Override
    public Page<CourseRepository.DateForm> findCoursesByTagIds(List<Long> tagIds, Pageable pageable) {
        return courseRepository.findAllByTagIds(tagIds, pageable);
    }

    @Override
    public Page<CourseRepository.RadiusForm> findCoursesByLocation(Point location, Pageable pageable) {
        return courseRepository.findAllByLocation(location, pageable);
    }

    @Override
    public Page<CourseRepository.RadiusForm> findCoursesByTagIdsAndLocation(List<Long> tagIds, Point location, Pageable pageable) {
        return courseRepository.findAllByTagIdsAndLocation(tagIds, location, pageable);
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

    @Override
    public List<Course> findNearCoursesByUserIdAndLocationAndState(
            UUID userId,
            Point location,
            EState state) {
        if (userId == null)
            return courseRepository.findNearCoursesByLocationAndState(location, state);
        else
            return courseRepository.findNearCoursesByUserIdAndLocationAndState(userId, location, state);
    }
}
