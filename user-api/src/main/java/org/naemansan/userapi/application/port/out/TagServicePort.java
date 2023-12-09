package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.dto.response.TagDto;

import java.util.List;

public interface TagServicePort {
    List<TagDto> findByTagDtoIds(List<Long> tagIds);
}
