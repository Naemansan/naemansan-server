package org.naemansan.courseapi.dto.response;

import lombok.Builder;
import org.naemansan.courseapi.dto.persistent.TagPersistent;

import java.util.List;

@Builder
public record CourseSimilarityDto(
        Long id,
        String title,
        String startLocationName,
        String distance,
        List<String> tags
) {
}
