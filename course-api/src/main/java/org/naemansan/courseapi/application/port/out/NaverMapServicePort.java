package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.common.LocationDto;

public interface NaverMapServicePort {
    String getLocationName(LocationDto locationDto);

}
