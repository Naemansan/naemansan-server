package org.naemansan.userapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record FollowDto(
        // 팔로잉 당할 사람의 uuid
        @NotNull
        String followedUuid
) {
}
