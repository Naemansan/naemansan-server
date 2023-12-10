package org.naemansan.userapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DeleteFollowCommand extends SelfValidating<DeleteFollowCommand> {
    @NotNull
    @Size(min = 36, max = 36)
    String followingId;
    @NotNull
    @Size(min = 36, max = 36)
    String followerId;

    @Builder
    public DeleteFollowCommand(String followingId, String followerId) {
        this.followingId = followingId;
        this.followerId = followerId;
        this.validateSelf();
    }
}
