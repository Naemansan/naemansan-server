package org.dongguk.userapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserNameDto(
        @JsonProperty("uuid")
        String uuid,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
}
