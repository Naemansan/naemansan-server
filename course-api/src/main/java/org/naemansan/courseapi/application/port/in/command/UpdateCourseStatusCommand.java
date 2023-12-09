package org.naemansan.courseapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class UpdateCourseStatusCommand extends SelfValidating<UpdateCourseStatusCommand> {
    @NotNull
    private final String userId;

    @NotNull
    private final Long courseId;

    @NotNull
    private final Boolean isEnrolled;

    private UpdateCourseStatusCommand(String userId, Long courseId, Boolean isEnrolled) {
        this.userId = userId;
        this.courseId = courseId;
        this.isEnrolled = isEnrolled;
    }

    public static UpdateCourseStatusCommand of(String userId, Long courseId, Boolean isEnrolled) {
        return new UpdateCourseStatusCommand(userId, courseId, isEnrolled);
    }
}
