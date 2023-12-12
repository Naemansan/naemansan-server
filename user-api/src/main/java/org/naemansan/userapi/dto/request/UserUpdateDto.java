package org.naemansan.userapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.naemansan.common.dto.request.ImageState;

import java.util.List;

public record UserUpdateDto(
        @JsonProperty("nickname")
        @Size(min = 1, max = 12)
        String nickname,

        @JsonProperty("introduction")
        @Size(min = 1, max = 100)
        String introduction,

        @JsonProperty("createdTagIds")
        @NotNull
        List<Long> createdTagIds,

        @JsonProperty("deletedTagIds")
        @NotNull
        List<Long> deletedTagIds,

        @JsonProperty("imageState")
        @NotNull
        ImageState imageState
) {
}
