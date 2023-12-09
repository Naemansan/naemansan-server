package org.naemansan.courseapi.dto.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.exception.ErrorCode;
import org.naemansan.common.exception.CommonException;

import java.util.Arrays;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public enum EEmotion {
    JOY("JOY"),
    COMFORT("COMFORT"),
    THANKS("THANKS"),
    ANXIETY("ANXIETY"),
    WOUND("WOUND"),
    SADNESS("SADNESS");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    public static EEmotion fromString(String value) {
        return Arrays.stream(EEmotion.values())
                .filter(emotion -> emotion.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }
}
