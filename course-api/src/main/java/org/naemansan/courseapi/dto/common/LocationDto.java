package org.naemansan.courseapi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record LocationDto(
        @JsonProperty("latitude")
        @NotNull
        double latitude,

        @JsonProperty("longitude")
        @NotNull
        double longitude
) {

    @Override
    public String toString() {
        return String.format("%f,%f", longitude, latitude);
    }
}
