package org.naemansan.courseapi.dto.persistent;

import org.locationtech.jts.geom.Point;
import org.naemansan.courseapi.dto.request.SpotDto;
import org.naemansan.courseapi.dto.type.ECategory;

public record SpotPersistent(
        String name,

        String description,

        Point location,

        String thumbnail,

        ECategory category

) {
    public static SpotPersistent fromDto(SpotDto dto, String thumbnail, Point location) {
        return new SpotPersistent(
                dto.name(),
                dto.description(),
                location,
                thumbnail,
                dto.category()
        );
    }
}
