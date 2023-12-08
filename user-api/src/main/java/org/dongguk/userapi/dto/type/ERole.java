package org.dongguk.userapi.dto.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ERole {
    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    @Override
    public String toString() {
        return this.value;
    }

    public static ERole fromString(String value) {
        return ERole.valueOf(value);
    }
}
