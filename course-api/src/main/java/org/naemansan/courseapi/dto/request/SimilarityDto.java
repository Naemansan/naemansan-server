package org.naemansan.courseapi.dto.request;

import jakarta.validation.constraints.NotNull;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.util.List;

public record SimilarityDto(
        @NotNull
        List<LocationDto> locations
) {
}
