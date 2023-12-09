package org.naemansan.courseapi.application.port.in.command;

import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class DeleteCourseCommand extends SelfValidating<DeleteCourseCommand> {
    private final Long courseId;
    private final String userId;

    private DeleteCourseCommand(String userId, Long courseId) {
        this.courseId = courseId;
        this.userId = userId;
        this.validateSelf();
    }

    public static DeleteCourseCommand of(String uuid, Long id) {
        return new DeleteCourseCommand(uuid, id);
    }
}
