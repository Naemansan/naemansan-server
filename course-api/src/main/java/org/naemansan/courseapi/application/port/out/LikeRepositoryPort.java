package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.domain.Course;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LikeRepositoryPort {

    Boolean existsByCourseAndUserId(Course course, UUID userId);

    Map<Long, Boolean> existsByCoursesAndUserId(List<Course> course, UUID userId);

    Long countByCourse(Course course);

    Map<Long, Long> countByCourses(List<Course> courses);
}
