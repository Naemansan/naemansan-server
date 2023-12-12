package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.common.TagDto;

import java.util.List;
import java.util.Map;

public interface TagServicePort {
    Map<Long, String> findByTagIds(List<Long> tagIds);
}
