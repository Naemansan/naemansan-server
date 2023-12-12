package org.naemansan.courseapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MomentDto(
        @JsonProperty("courseId")
        @NotNull
        Long courseId,

        @JsonProperty("content")
        @Size(min = 1, max = 300)
        String content
) {
}
