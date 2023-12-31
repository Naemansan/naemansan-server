package org.naemansan.userapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.naemansan.common.dto.request.ProfileImageState;

import java.util.List;

public record UserUpdateDto(
        @JsonProperty("nickname")
        @Size(min = 1, max = 12)
        String nickname,

        @JsonProperty("introduction")
        @Size(min = 1, max = 100)
        String introduction,

        @JsonProperty("createdTagIds")
        List<Long> createdTagIds,

        @JsonProperty("deletedTagIds")
        List<Long> deletedTagIds,

        @JsonProperty("imageState")
        @NotNull
        ProfileImageState imageState
) {
}
