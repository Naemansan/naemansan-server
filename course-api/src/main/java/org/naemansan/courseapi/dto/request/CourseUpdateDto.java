package org.naemansan.courseapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.util.List;

public record CourseUpdateDto(
        @JsonProperty("title")
        @Size(min = 1, max = 12)
        String title,

        @JsonProperty("content")
        @Size(min = 1, max = 300)
        String content,

        @JsonProperty("createTagIds")
        List<Long> createTagIds,

        @JsonProperty("deleteTagIds")
        List<Long> deleteTagIds,

        @JsonProperty("createSpots")
        List<SpotDto> createSpots,

        @JsonProperty("deleteSpotIds")
        List<Long> deleteSpotIds
) {
}
