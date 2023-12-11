package org.naemansan.courseapi.application.port.in.query;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReadSimilarEnrolledCoursesCommand extends SelfValidating<ReadSimilarEnrolledCoursesCommand> {
    private final List<LocationDto> locations;

    @Builder
    public ReadSimilarEnrolledCoursesCommand(List<LocationDto> locations) {
        this.locations = locations;
        this.validateSelf();
    }
}
