package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.dto.response.TagDto;

import java.util.List;
import java.util.Map;

public interface TagServicePort {
    List<TagDto> findByTagDtoIds(List<Long> tagIds);

}
