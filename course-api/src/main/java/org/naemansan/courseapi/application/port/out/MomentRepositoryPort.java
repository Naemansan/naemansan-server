package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.domain.Course;

import java.util.List;
import java.util.Map;

public interface MomentRepositoryPort {

    Long countByCourse(Course course);

    Map<Long, Long> countByCourses(List<Course> courses);
}
