package org.naemansan.tagapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.tagapi.adapter.out.repository.TagRepository;
import org.naemansan.tagapi.application.port.out.FindTagPort;
import org.naemansan.tagapi.domain.Tag;
import org.naemansan.tagapi.dto.request.TagDto;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class FindTagPersistenceAdapter implements FindTagPort {
    private final TagRepository tagRepository;

    @Override
    public List<TagDto> findAll(List<Long> ids) {
        List<Tag> tags = new ArrayList<>();
        if (ids != null && !ids.isEmpty()) {
            tags.addAll(tagRepository.findAllById(ids));
        } else {
            tags.addAll(tagRepository.findAll());
        }

        return tags.stream().map(
                tag -> TagDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .build()).toList();
    }
}
