package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.ECategory;

@Builder
public record SpotListDto(
        @JsonProperty("name")
        String name,

        @JsonProperty("description")
        String description,

        @JsonProperty("location")
        LocationDto location,

        @JsonProperty("category")
        ECategory category,

        @JsonProperty("thumbnailUrl")
        String thumbnailUrl
) {
}
