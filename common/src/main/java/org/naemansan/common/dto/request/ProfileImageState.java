package org.naemansan.common.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProfileImageState(
        String type,

        @NotNull
        Boolean isChanged
) {
}
