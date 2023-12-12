package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.naemansan.courseapi.dto.common.TagDto;

import java.util.List;

@Builder
public record CourseSimilarityDto(
        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String title,

        @JsonProperty("startLocationName")
        String startLocationName,

        @JsonProperty("distance")
        String distance,

        @JsonProperty("tags")
        List<TagDto> tags
) {
}
