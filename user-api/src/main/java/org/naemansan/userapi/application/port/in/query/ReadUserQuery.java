package org.naemansan.userapi.application.port.in.query;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class ReadUserQuery extends SelfValidating<ReadUserQuery> {
    @NotNull
    private final String uuid;

    private ReadUserQuery(String uuid) {
        this.uuid = uuid;
        this.validateSelf();
    }

    public static ReadUserQuery of(String uuid) {
        return new ReadUserQuery(uuid);
    }
}
