package org.naemansan.userapi.application.port.in.query;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.naemansan.common.SelfValidating;

@Getter
public class FindUserQuery extends SelfValidating<FindUserQuery> {
    @NotNull
    private final String uuid;

    private FindUserQuery(String uuid) {
        this.uuid = uuid;
        this.validateSelf();
    }

    public static FindUserQuery of(String uuid) {
        return new FindUserQuery(uuid);
    }
}
