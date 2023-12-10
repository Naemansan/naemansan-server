package org.naemansan.userapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateFollowCommand extends SelfValidating<CreateFollowCommand> {
    @NotNull
    @Size(min = 36, max = 36)
    String followingId;
    @NotNull
    @Size(min = 36, max = 36)
    String followerId;

    public CreateFollowCommand(String followingId, String followerId) {
        this.followingId = followingId;
        this.followerId = followerId;
        this.validateSelf();
    }
}
