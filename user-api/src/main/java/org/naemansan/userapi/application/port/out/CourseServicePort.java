package org.naemansan.userapi.application.port.out;

import java.util.Map;

public interface CourseServicePort {
    Map<String, Object> getCourseList(String filter, Integer page, Integer size);
}
