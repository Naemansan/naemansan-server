package org.naemansan.userapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record FollowDto(
        @JsonProperty("userId")
        @NotNull
        String userId
) {
}
