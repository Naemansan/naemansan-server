package org.naemansan.userapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserNameDto(
        @JsonProperty("id")
        String id,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
}
