package org.naemansan.courseapi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TagDto(
        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String name
) {
}
