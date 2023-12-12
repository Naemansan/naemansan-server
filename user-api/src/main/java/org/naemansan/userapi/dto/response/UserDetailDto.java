package org.naemansan.userapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDetailDto(
        @JsonProperty("id")
        String id,

        @JsonProperty("nickname")
        String nickname,

        @JsonProperty("introduction")
        String introduction,

        @JsonProperty("profileImageUrl")
        String profileImageUrl,

        @JsonProperty("tags")
        List<TagDto> tags,

        @JsonProperty("followingCount")
        Long followingCount,

        @JsonProperty("followerCount")
        Long followerCount
) {
}
