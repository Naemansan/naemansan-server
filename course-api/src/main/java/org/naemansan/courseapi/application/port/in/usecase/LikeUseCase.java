package org.naemansan.courseapi.application.port.in.usecase;

import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteLikeCommand;
import org.naemansan.courseapi.application.port.in.command.ReadLikeStatusCommand;
import org.naemansan.courseapi.application.port.in.command.ReadLikesStatusCommand;
import org.naemansan.courseapi.dto.response.LikeStatusDto;

import java.util.List;

public interface LikeUseCase {
    void createLike(CreateLikeCommand command);
    void deleteLike(DeleteLikeCommand command);

    LikeStatusDto readLikeStatus(ReadLikeStatusCommand command);

    List<LikeStatusDto> readLikesStatus(ReadLikesStatusCommand command);
}
