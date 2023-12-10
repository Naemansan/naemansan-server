package org.naemansan.courseapi.application.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class DeleteLikeCommand extends SelfValidating<DeleteLikeCommand> {
    private final String userId;
    private final Long courseId;

    @Builder
    public DeleteLikeCommand(String userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.validateSelf();
    }
}
