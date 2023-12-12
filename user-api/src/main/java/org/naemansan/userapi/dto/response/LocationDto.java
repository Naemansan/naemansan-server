package org.naemansan.userapi.dto.response;

public record LocationDto(
        double latitude,
        double longitude) {
    @Override
    public String toString() {
        return String.format("%f,%f", longitude, latitude);
    }
}
