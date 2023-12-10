package org.naemansan.courseapi.application.port.in.usecase;

import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteLikeCommand;

public interface LikeUseCase {
    void createLike(CreateLikeCommand command);
    void deleteLike(DeleteLikeCommand command);
}
