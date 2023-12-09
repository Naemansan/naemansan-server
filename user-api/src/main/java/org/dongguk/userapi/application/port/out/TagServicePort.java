package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.dto.response.TagDto;

import java.util.List;

public interface TagServicePort {
    List<TagDto> findByTagDtoIds(List<Long> tagIds);
}
