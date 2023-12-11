package org.naemansan.courseapi.dto.persistent;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserNamePersistent(
        @JsonProperty("uuid")
        String uuid,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
        public static UserNamePersistent of(String uuid, String nickname, String profileImageUrl) {
                return new UserNamePersistent(uuid, nickname, profileImageUrl);
        }
}
