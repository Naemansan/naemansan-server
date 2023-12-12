package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.common.TagDto;

import java.util.List;

@Builder
public record CourseListDto(
        @JsonProperty("id")
        Long id,

        @JsonProperty("title")
        String title,

        @JsonProperty("startLocationName")
        String startLocationName,

        @JsonProperty("distance")
        String distance,

        @JsonProperty("tags")
        List<TagDto> tags,

        @JsonProperty("locations")
        List<LocationDto> locations,

        @JsonProperty("momentCount")
        Long momentCount,

        @JsonProperty("likeCount")
        Long likeCount
) {
}
