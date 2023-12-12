package org.naemansan.courseapi.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EFilter {
    ALL("ALL"),
    PUBLIC("ACTIVE"),
    PRIVATE("INACTIVE"),
    LIKE("LIKE");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
