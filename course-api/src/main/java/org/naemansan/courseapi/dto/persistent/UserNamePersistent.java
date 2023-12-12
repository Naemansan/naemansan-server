package org.naemansan.courseapi.dto.persistent;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserNamePersistent(
        @JsonProperty("id")
        String id,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
        public static UserNamePersistent of(String id, String nickname, String profileImageUrl) {
                return new UserNamePersistent(id, nickname, profileImageUrl);
        }
}
