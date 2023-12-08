package org.dongguk.userapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FollowListDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("user_uuid")
        String userUuid,

        @JsonProperty("nickname")
        String nickname,

        @JsonProperty("introduction")
        String introduction,

        @JsonProperty("profileImageUrl")
        String profileImageUrl
) {
}
