package org.naemansan.courseapi.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CourseListDto(
        Long id,
        String title,
        String startLocationName,
        String distance,
        List<String> tags,
        Long momentCount,
        Long likeCount
) {
}
