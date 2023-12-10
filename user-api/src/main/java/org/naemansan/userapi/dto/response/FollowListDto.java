package org.naemansan.userapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FollowListDto(
        @JsonProperty("userId")
        String userId,

        @JsonProperty("nickname")
        String nickname,

        @JsonProperty("introduction")
        String introduction,

        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
}
