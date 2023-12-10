package org.naemansan.courseapi.application.port.in.usecase;

import org.naemansan.courseapi.application.port.in.query.ReadCourseDependenceCommand;
import org.naemansan.courseapi.dto.response.SpotListDto;

import java.util.List;

public interface SpotUseCase {
    List<SpotListDto> findSpots();

    List<SpotListDto> findSpotsByCourseId(ReadCourseDependenceCommand command);
}
