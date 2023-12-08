package org.dongguk.userapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {
    @NotNull
    @Size(min = 36, max = 36)
    private final String uuid;

    @NotNull
    @Size(min = 1, max = 20)
    private final String nickname;

    @NotNull
    @Size(min = 1, max = 100)
    private final String introduction;

    public UpdateUserCommand(String uuid, String nickname, String introduction) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.introduction = introduction;
        this.validateSelf();
    }
}
