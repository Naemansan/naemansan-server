package org.naemansan.courseapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.naemansan.common.dto.request.ProfileImageState;
import org.naemansan.common.dto.request.SpotImageState;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.ECategory;

public record SpotDto(
        @JsonProperty("name")
        @Size(min = 1, max = 12)
        String name,

        @JsonProperty("description")
        @Size(min = 1, max = 100)
        String description,

        @JsonProperty("location")
        @NotNull
        LocationDto location,

        @JsonProperty("category")
        @NotNull
        ECategory category,

        @JsonProperty("imageState")
        @NotNull
        SpotImageState imageState
) {
}
