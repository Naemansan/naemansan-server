package org.naemansan.courseapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReadLikeStatusCommand extends SelfValidating<ReadLikeStatusCommand> {
    @NotNull
    private final String userId;
    @NotNull
    private final Long courseId;

    @Builder
    public ReadLikeStatusCommand(String userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.validateSelf();
    }
}
