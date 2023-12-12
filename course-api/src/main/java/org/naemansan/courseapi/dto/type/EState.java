package org.naemansan.courseapi.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EState {
    DELETED("DELETED"),
    IN_PROGRESS("IN_PROGRESS"),
    PERSONAL("PERSONAL"),
    ENROLLED("ENROLLED");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
