package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.dto.response.TagDto;

import java.util.List;

public interface FindTagNamesPort {

    List<TagDto> findAll(List<Long> ids);
}
