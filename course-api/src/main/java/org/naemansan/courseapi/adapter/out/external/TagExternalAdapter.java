package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.TagServicePort;
import org.naemansan.courseapi.utility.InternalClientUtil;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class TagExternalAdapter implements TagServicePort {
    private final InternalClientUtil internalClientUtil;

    @Override
    public List<String> findByTagIds(List<Long> tagIds) {
        return internalClientUtil.getTagNames(tagIds);
    }
}
