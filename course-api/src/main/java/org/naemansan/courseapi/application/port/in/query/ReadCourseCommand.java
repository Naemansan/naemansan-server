package org.naemansan.courseapi.application.port.in.query;

import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class ReadCourseCommand extends SelfValidating<ReadCourseCommand> {
    private final Long id;

    private ReadCourseCommand(Long id) {
        this.id = id;
        this.validateSelf();
    }

    public static ReadCourseCommand of(Long id) {
        return new ReadCourseCommand(id);
    }
}
