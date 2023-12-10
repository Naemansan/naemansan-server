package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.WeatherServicePort;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.type.EWeather;
import org.naemansan.courseapi.utility.ExternalClientUtil;
import org.naemansan.courseapi.utility.InternalClientUtil;

@WebAdapter
@RequiredArgsConstructor
public class WeatherExternalAdapter implements WeatherServicePort {
    private final ExternalClientUtil externalClientUtil;

    @Override
    public EWeather getCurrentWeather(LocationDto location) {
        return externalClientUtil.getWeather(location);
    }
}
