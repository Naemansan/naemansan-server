package org.naemansan.userapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.naemansan.common.dto.request.ImageState;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {
    @NotNull
    @Size(min = 36, max = 36)
    private final String id;

    @NotNull
    @Size(min = 1, max = 20)
    private final String nickname;

    @NotNull
    @Size(min = 1, max = 100)
    private final String introduction;

    @NotNull
    private final List<Long> createdTagIds;

    @NotNull
    private final List<Long> deletedTagIds;

    @NotNull
    private final ImageState imageState;

    @Builder
    private UpdateUserCommand(
            String id,
            String nickname,
            String introduction,
            List<Long> createdTagIds,
            List<Long> deletedTagIds,
            ImageState imageState) {
        this.id = id;
        this.nickname = nickname;
        this.introduction = introduction;
        this.createdTagIds = createdTagIds;
        this.deletedTagIds = deletedTagIds;
        this.imageState = imageState;
        this.validateSelf();
    }
}
