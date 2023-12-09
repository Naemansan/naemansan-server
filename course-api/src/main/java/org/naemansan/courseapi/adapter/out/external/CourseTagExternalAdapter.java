package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.CourseTagServicePort;
import org.naemansan.courseapi.utility.ClientUtil;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class CourseTagExternalAdapter implements CourseTagServicePort {
    private final ClientUtil clientUtil;

    @Override
    public List<String> findByTagIds(List<Long> tagIds) {
        return clientUtil.getTagNames(tagIds);
    }
}
