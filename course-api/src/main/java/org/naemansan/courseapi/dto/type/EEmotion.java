package org.naemansan.courseapi.dto.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;

import java.util.Arrays;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public enum EEmotion {
    JOY("JOY"),
    COMFORT("COMFORT"),
    THANKS("THANKS"),
    ANXIETY("ANXIETY"),
    FLUTTER("FLUTTER"),
    SADNESS("SADNESS");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
