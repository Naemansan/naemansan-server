package org.dongguk.userapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
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

    @NotNull
    private final List<Long> tagIds;

    private final MultipartFile profileImage;

    private UpdateUserCommand(String uuid, String nickname, String introduction, List<Long> tagIds, MultipartFile profileImage) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.introduction = introduction;
        this.tagIds = tagIds;
        this.profileImage = profileImage;
        this.validateSelf();
    }

    public static UpdateUserCommand of(String uuid, String nickname, String introduction, List<Long> tagIds, MultipartFile profileImage) {
        return new UpdateUserCommand(uuid, nickname, introduction, tagIds, profileImage);
    }
}
