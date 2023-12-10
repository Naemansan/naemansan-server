package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.EWeather;

public interface WeatherServicePort {
    EWeather getCurrentWeather(LocationDto location);
}
