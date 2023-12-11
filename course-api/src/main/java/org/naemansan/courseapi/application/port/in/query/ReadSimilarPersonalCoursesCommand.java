package org.naemansan.courseapi.application.port.in.query;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReadSimilarPersonalCoursesCommand extends SelfValidating<ReadSimilarPersonalCoursesCommand> {
    private final String userId;
    private final List<LocationDto> locations;

    @Builder
    public ReadSimilarPersonalCoursesCommand(String userId, List<LocationDto> locations) {
        this.userId = userId;
        this.locations = locations;
        this.validateSelf();
    }
}
