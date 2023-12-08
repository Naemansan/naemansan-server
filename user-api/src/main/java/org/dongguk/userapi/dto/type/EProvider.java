package org.dongguk.userapi.dto.type;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum EProvider {
    DEFAULT("DEFAULT"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE");

    private final String value;

    @Override
    public String toString() {
        return this.value;
    }

    public static EProvider fromString(String value) {
        return Arrays.stream(EProvider.values())
                .filter(v -> v.toString().equals(value))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
