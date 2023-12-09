package org.naemansan.courseapi.dto.common;

public record LocationDto(
        double latitude,
        double longitude
) {

    @Override
    public String toString() {
        return String.format("%f,%f", longitude, latitude);
    }
}
