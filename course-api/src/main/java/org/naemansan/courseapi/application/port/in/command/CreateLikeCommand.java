package org.naemansan.courseapi.application.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class CreateLikeCommand extends SelfValidating<CreateLikeCommand> {
    private final String userId;
    private final Long courseId;

    @Builder
    public CreateLikeCommand(String userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.validateSelf();
    }
}
