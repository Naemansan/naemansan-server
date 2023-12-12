package org.naemansan.common.dto.request;

import jakarta.validation.constraints.NotNull;

public record SpotImageState(
        String type,

        @NotNull
        Boolean isUsed
) {
}
