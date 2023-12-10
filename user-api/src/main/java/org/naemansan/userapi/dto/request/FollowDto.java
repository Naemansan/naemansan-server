package org.naemansan.userapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record FollowDto(
        @NotNull
        String userId
) {
}
