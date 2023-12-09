package org.naemansan.courseapi.application.port.in.query;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.util.List;

@Getter
public class ReadCoursesCommand extends SelfValidating<ReadCoursesCommand> {
    private final List<Long> tagIds;
    private final LocationDto location;

    @NotNull
    private final Integer page;

    @NotNull
    private final Integer size;

    private ReadCoursesCommand(List<Long> tagIds, LocationDto location, Integer page, Integer size) {
        this.tagIds = tagIds;
        this.location = location;
        this.page = page;
        this.size = size;
        this.validateSelf();
    }

    public static ReadCoursesCommand of(
            List<Long> tagIds,
            LocationDto location,
            Integer page,
            Integer size) {
        return new ReadCoursesCommand(tagIds, location, page, size);
    }
}
