package org.naemansan.courseapi.dto.persistent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TagPersistent(
        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String name
) {
}
