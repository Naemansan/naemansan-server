package org.naemansan.courseapi.application.port.in.query;

import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
@Builder
public class ReadMomentsCommand extends SelfValidating<ReadMomentsCommand> {
    private final Integer page;
    private final Integer size;

    public ReadMomentsCommand(Integer page, Integer size) {
        this.page = page;
        this.size = size;
        this.validateSelf();
    }
}
