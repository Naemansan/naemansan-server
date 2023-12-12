package org.naemansan.common.dto.request;

import jakarta.validation.constraints.NotNull;

public record ImageState(
        @NotNull
        String type,

        @NotNull
        Boolean isChanged
) {
}
