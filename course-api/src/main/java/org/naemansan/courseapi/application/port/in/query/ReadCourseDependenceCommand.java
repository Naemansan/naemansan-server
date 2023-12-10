package org.naemansan.courseapi.application.port.in.query;

import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@Builder
public class ReadCourseDependenceCommand extends SelfValidating<ReadCourseDependenceCommand> {
    private final Long courseId;
    private final Integer page;
    private final Integer size;

    public ReadCourseDependenceCommand(Long courseId, Integer page, Integer size) {
        this.courseId = courseId;
        this.page = page;
        this.size = size;
        this.validateSelf();
    }
}
