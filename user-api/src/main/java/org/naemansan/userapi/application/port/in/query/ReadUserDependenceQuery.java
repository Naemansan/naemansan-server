package org.naemansan.userapi.application.port.in.query;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class ReadUserDependenceQuery extends SelfValidating<ReadUserDependenceQuery> {
    @NotNull
    private final String userId;

    @NotNull
    private final Integer page;

    @NotNull
    private final Integer size;

    @Builder
    public ReadUserDependenceQuery(String userId, Integer page, Integer size) {
        this.userId = userId;
        this.page = page;
        this.size = size;
        this.validateSelf();
    }
}
