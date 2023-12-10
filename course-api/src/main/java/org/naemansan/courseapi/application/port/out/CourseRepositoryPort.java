package org.naemansan.courseapi.application.port.out;

import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.naemansan.courseapi.adapter.out.repository.CourseRepository;
import org.naemansan.courseapi.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CourseRepositoryPort {
    Course craeteCourse(
            String title,
            String content,
            String startLocationName,
            MultiPoint locations,
            Double distance,
            UUID createdUserId);

    Page<Course> findCourses(Pageable pageable);

    Map<Long, Course> findCoursesByIds(List<Long> ids);

    Page<CourseRepository.DateForm> findCoursesByTagIds(List<Long> tagIds, Pageable pageable);

    Page<CourseRepository.LocationForm> findCoursesByLocation(Point location, Pageable pageable);

    Page<CourseRepository.LocationForm> findCoursesByTagIdsAndLocation(List<Long> tagIds, Point location, Pageable pageable);

    Course findCourseById(Long id);

    Course findCourseByIdAndUserId(Long id, UUID userId);

    void deleteCourse(Course course);


}
