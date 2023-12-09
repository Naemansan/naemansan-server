package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;

import java.util.List;

public interface CourseTagRepositoryPort {
    List<CourseTag> createCourseTags(List<Long> tagIds, Course course);

    void deleteCourseTags(List<CourseTag> courseTags);
}
