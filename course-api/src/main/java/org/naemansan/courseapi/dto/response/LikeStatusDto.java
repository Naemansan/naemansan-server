package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LikeStatusDto(
        @JsonProperty("courseId")
        Long courseId,

        @JsonProperty("isLiked")
        Boolean isLiked
) {
}
