package org.naemansan.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.userapi.application.port.out.CourseServicePort;
import org.naemansan.userapi.dto.response.CourseListDto;
import org.naemansan.userapi.utility.ClientUtil;

import java.util.List;
import java.util.Map;

@WebAdapter
@RequiredArgsConstructor
public class CourseExternalAdapter implements CourseServicePort {
    private final ClientUtil clientUtil;

    @Override
    public Map<String, Object> getCourseList(String filter, Integer page, Integer size) {
        return clientUtil.getCourseList(filter, page, size);
    }
}
