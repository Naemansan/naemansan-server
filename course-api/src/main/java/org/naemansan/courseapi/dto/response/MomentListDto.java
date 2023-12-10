package org.naemansan.courseapi.dto.response;

import lombok.Builder;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.dto.type.EWeather;

@Builder
public record MomentListDto(
        Long id,
        Long courseId,
        String courseTitle,
        String nickname,
        String content,
        String createdAt,
        EWeather weather,
        EEmotion emotion
) {
}
