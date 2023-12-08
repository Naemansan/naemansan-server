package org.dongguk.userapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class DeleteFollowCommand extends SelfValidating<DeleteFollowCommand> {
    @NotNull
    private final Long followId;

    public DeleteFollowCommand(Long followId) {
        this.followId = followId;
        this.validateSelf();
    }
}
