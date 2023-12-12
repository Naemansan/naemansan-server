package org.naemansan.courseapi.application.port.in.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReadLikesStatusCommand extends SelfValidating<ReadLikesStatusCommand> {
    private final String userId;
    private final List<Long> courseIds;

    @Builder
    public ReadLikesStatusCommand(String userId, List<Long> courseIds) {
        this.userId = userId;
        this.courseIds = courseIds;
        this.validateSelf();
    }
}
