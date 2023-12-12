package org.naemansan.courseapi.application.port.in.query;

import lombok.Builder;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

import java.util.UUID;

@Getter
@Builder
public class ReadCoursesForUserCommand extends SelfValidating<ReadCoursesForUserCommand> {
    private final String userId;
    private final String filter;
    private final Integer page;
    private final Integer size;

    public ReadCoursesForUserCommand(String userId, String filter, Integer page, Integer size) {
        this.userId = userId;
        this.filter = filter;
        this.page = page;
        this.size = size;
        this.validateSelf();
    }

    public static ReadCoursesForUserCommand of(String userId, String filter, Integer page, Integer size) {
        return new ReadCoursesForUserCommand(userId, filter, page, size);
    }
}
