package org.naemansan.tagapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.tagapi.application.port.in.usecase.TagQueryUseCase;
import org.naemansan.tagapi.application.port.out.FindTagPort;
import org.naemansan.tagapi.dto.response.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagQueryService implements TagQueryUseCase {
    private final FindTagPort findTagPort;

    @Override
    public List<TagDto> findAll(List<Long> ids) {
        return findTagPort.findAll(ids);
    }
}
