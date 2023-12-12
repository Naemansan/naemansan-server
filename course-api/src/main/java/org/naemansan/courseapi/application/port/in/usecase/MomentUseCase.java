package org.naemansan.courseapi.application.port.in.usecase;

import org.naemansan.courseapi.application.port.in.command.CreateMomentCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseDependenceCommand;
import org.naemansan.courseapi.application.port.in.query.ReadMomentsCommand;
import org.naemansan.courseapi.dto.response.MomentListDto;

import java.util.Map;

public interface MomentUseCase {
    MomentListDto createMoment(CreateMomentCommand command);

    Map<String, Object> findMoments(ReadMomentsCommand command);

    Map<String, Object> findMomentsByCourseId(ReadCourseDependenceCommand command);
}
