package org.naemansan.courseapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.ECategory;

public record SpotDto(
        @JsonProperty("title")
        @Size(min = 1, max = 12)
        String title,

        @JsonProperty("content")
        @Size(min = 1, max = 100)
        String content,

        @JsonProperty("location")
        @NotNull
        LocationDto location,

        @JsonProperty("category")
        @NotNull
        ECategory category,

        @JsonProperty("useImage")
        @NotNull
        Boolean useImage
) {
}
