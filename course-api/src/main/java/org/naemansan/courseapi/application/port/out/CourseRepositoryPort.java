package org.naemansan.courseapi.application.port.out;

import org.locationtech.jts.geom.MultiPoint;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;

import java.util.List;
import java.util.UUID;

public interface CourseRepositoryPort {
    Course craeteCourse(
            String title, String content,
            String startLocationName, MultiPoint locations, Double distance,
            UUID createdUserId);

    List<CourseTag> createCourseTags(List<Long> tagIds, Course course);

    Course findCourseById(Long id);

    Course findCourseByIdAndUserId(Long id, UUID userId);

    void deleteCourse(Course course);
}
