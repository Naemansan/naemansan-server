package org.naemansan.courseapi.dto.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;

import java.util.Arrays;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public enum ECategory {
    PUB_BAR("PUB_BAR"),
    CAFE_BAKERY("CAFE_BAKERY"),
    RESTAURANT("RESTAURANT"),
    NATURE("NATURE"),
    SHOPPING("SHOPPING"),
    ACCOMMODATION("ACCOMMODATION"),
    PLACE("PLACE");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    public static ECategory fromString(String value) {
        return Arrays.stream(ECategory.values())
                .filter(emotion -> emotion.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }
}
