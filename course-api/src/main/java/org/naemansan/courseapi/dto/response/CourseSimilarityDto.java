package org.naemansan.courseapi.dto.response;

import lombok.Builder;
import org.naemansan.courseapi.dto.persistent.TagPersistent;

import java.util.List;

@Builder
public record CourseSimilarityDto(
        Long id,
        String title,
        String startLocationName,
        Double distance,
        List<String> tags
) {
}
