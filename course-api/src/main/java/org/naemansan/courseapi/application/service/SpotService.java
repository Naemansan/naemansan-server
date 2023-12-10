package org.naemansan.courseapi.application.service;

import org.naemansan.courseapi.application.port.in.query.ReadCourseDependenceCommand;
import org.naemansan.courseapi.application.port.in.usecase.SpotUseCase;
import org.naemansan.courseapi.dto.response.SpotListDto;

import java.util.List;

public class SpotService implements SpotUseCase {
    @Override
    public List<SpotListDto> findSpots() {
        return null;
    }

    @Override
    public List<SpotListDto> findSpotsByCourseId(ReadCourseDependenceCommand command) {
        return null;
    }
}
