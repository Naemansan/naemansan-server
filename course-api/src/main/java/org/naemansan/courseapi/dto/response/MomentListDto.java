package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.dto.type.EWeather;

@Builder
public record MomentListDto(
        @JsonProperty("id")
        Long id,

        @JsonProperty("courseId")
        Long courseId,

        @JsonProperty("courseTitle")
        String courseTitle,

        @JsonProperty("nickname")
        String nickname,

        @JsonProperty("description")
        String content,

        @JsonProperty("createdAt")
        String createdAt,

        @JsonProperty("weather")
        EWeather weather,

        @JsonProperty("emotion")
        EEmotion emotion
) {
}
