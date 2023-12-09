package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.domain.Course;

import java.util.List;

public interface CourseTagServicePort {
    List<String> findByTagIds(List<Long> tagIds);
}
