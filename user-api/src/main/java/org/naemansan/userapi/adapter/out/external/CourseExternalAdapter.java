package org.naemansan.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.userapi.application.port.out.CourseServicePort;
import org.naemansan.userapi.dto.response.CourseListDto;
import org.naemansan.userapi.utility.ClientUtil;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class CourseExternalAdapter implements CourseServicePort {
    private final ClientUtil clientUtil;

    @Override
    public List<CourseListDto> findByUserIdAndIsDeleted() {

    }

    @Override
    public List<CourseListDto> findByUserIdAndIsEnrolledAndIsDeleted() {

    }


}
