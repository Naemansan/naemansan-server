package org.dongguk.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.application.port.out.TagServicePort;
import org.dongguk.userapi.dto.response.TagDto;
import org.dongguk.userapi.utility.ClientUtil;
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
