package org.naemansan.userapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDetailDto(
        @JsonProperty("uuid")
        String uuid,

        @JsonProperty("nickname")
        String nickname,

        @JsonProperty("introduction")
        String introduction,

        @JsonProperty("profile_image_url")
        String profileImageUrl,

        @JsonProperty("tags")
        List<TagDto> tags
) {
}
