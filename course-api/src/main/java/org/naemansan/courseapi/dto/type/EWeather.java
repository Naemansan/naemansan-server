package org.naemansan.courseapi.dto.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;

import java.util.Arrays;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public enum EWeather {
    SUNNY_DAY("SUNNY_DAY"),
    SUNNY_NIGHT("SUNNY_NIGHT"),
    CLOUDY_DAY("CLOUDY_DAY"),
    CLOUDY_NIGHT("CLOUDY_NIGHT"),
    CLOUDY("CLOUDY"),
    RAIN("RAIN"),
    SNOW("SNOW");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    public static EWeather fromString(String value) {
        return Arrays.stream(EWeather.values())
                .filter(weather -> weather.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }
}
