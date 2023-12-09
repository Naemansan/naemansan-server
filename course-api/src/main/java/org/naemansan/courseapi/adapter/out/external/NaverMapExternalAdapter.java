package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.NaverMapServicePort;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.utility.ClientUtil;

@WebAdapter
@RequiredArgsConstructor
public class NaverMapExternalAdapter implements NaverMapServicePort {
    private final ClientUtil clientUtil;

    @Override
    public String getLocationName(LocationDto locationDto) {
        return clientUtil.getLocationName(locationDto);
    }
}
