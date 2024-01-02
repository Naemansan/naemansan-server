package org.naemansan.userapi.application.port.in.query;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
@Getter
public class ReadUserDependenceCourse extends SelfValidating<ReadUserDependenceCourse> {
    @NotNull
    private final String userId;

    @NotNull
    private final String filter;

    @NotNull
    private final Integer page;

    @NotNull
    private final Integer size;

    @Builder
    public ReadUserDependenceCourse(String userId, String filter, Integer page, Integer size) {
        this.userId = userId;
        this.filter = filter;
        this.page = page;
        this.size = size;
        this.validateSelf();
    }
}
