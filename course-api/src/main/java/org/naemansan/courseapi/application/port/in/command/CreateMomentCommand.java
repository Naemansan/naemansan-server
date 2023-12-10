package org.naemansan.courseapi.application.port.in.command;

import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class CreateMomentCommand extends SelfValidating<CreateMomentCommand> {
    private final String userId;
    private final Long courseId;
    private final String content;

    private CreateMomentCommand(String userId, Long courseId, String content) {
        this.userId = userId;
        this.courseId = courseId;
        this.content = content;
        this.validateSelf();
    }

    public static CreateMomentCommand of(String userId, Long courseId, String content) {
        return new CreateMomentCommand(userId, courseId, content);
    }
}
