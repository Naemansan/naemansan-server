package org.naemansan.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.application.port.out.TagServicePort;
import org.naemansan.userapi.dto.response.TagDto;
import org.naemansan.userapi.utility.ClientUtil;
import org.naemansan.common.annotaion.WebAdapter;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class TagExternalAdapter implements TagServicePort {
    private final ClientUtil clientUtil;

    @Override
    public List<TagDto> findByTagDtoIds(List<Long> tagIds) {
        return clientUtil.getTagDtos(tagIds);
    }
}
