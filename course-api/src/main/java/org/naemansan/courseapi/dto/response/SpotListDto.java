package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.ECategory;

@Builder
public record SpotListDto(
        @JsonProperty("title")
        @Size(min = 1, max = 12)
        String name,

        @JsonProperty("content")
        @Size(min = 1, max = 100)
        String description,

        @JsonProperty("location")
        @NotNull
        LocationDto location,

        @JsonProperty("category")
        @NotNull
        ECategory category,

        @JsonProperty("thumbnailUrl")
        @NotNull
        String thumbnailUrl
) {
}
