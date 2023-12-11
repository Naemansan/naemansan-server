package org.naemansan.courseapi.dto.persistent;

import lombok.Builder;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.util.List;

@Builder
public record CourseLocationsPersistent(
        Long id,
        List<LocationDto> locations
) {
}
