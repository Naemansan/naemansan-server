package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.NaverMapServicePort;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.utility.ExternalClientUtil;
import org.naemansan.courseapi.utility.InternalClientUtil;

@WebAdapter
@RequiredArgsConstructor
public class NaverMapExternalAdapter implements NaverMapServicePort {
    private final ExternalClientUtil externalClientUtil;

    @Override
    public String getLocationName(LocationDto locationDto) {
        return externalClientUtil.getLocationName(locationDto);
    }
}
