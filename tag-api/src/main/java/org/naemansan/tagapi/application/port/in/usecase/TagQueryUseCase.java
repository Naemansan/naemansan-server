package org.naemansan.tagapi.application.port.in.usecase;

import org.naemansan.tagapi.dto.request.TagDto;

import java.util.List;

public interface TagQueryUseCase {
    List<TagDto> findAll(List<Long> ids);
}
