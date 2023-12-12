package org.naemansan.courseapi.dto.response;

import lombok.Builder;

@Builder
public record LikeStatusDto(
        Long courseId,
        Boolean isLiked
) {
}
