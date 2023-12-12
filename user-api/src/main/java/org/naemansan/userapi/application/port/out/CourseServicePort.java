package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.dto.response.CourseListDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseServicePort {
    List<CourseListDto> findByUserIdAndIsDeleted();

    List<CourseListDto> findByUserIdAndIsEnrolledAndIsDeleted();

}
