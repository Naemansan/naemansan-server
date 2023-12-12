package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Like;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LikeRepositoryPort {
    void createLike(String userId, Course course);

    Like findLikeByUserIdAndCourse(String userId, Course course);

    void deleteLike(Like like);

    Boolean existsByUserIdAndCourse(UUID userId, Course course);

    Map<Long, Boolean> existsByUserIdAndCourses(UUID userId, List<Course> courses);

    Long countByCourse(Course course);

    Map<Long, Long> countByCourses(List<Course> courses);
}
