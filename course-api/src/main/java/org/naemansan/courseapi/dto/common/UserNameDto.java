package org.naemansan.courseapi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserNameDto(
        @JsonProperty("uuid")
        String uuid,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
        public static UserNameDto of(String uuid, String nickname, String profileImageUrl) {
                return new UserNameDto(uuid, nickname, profileImageUrl);
        }
}
