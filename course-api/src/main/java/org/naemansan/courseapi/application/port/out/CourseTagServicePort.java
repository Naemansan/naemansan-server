package org.naemansan.courseapi.application.port.out;

import java.util.List;

public interface CourseTagServicePort {
    List<String> findByTagIds(List<Long> tagIds);
}
