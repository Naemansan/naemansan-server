package org.naemansan.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.naemansan.common.exception.ErrorCode;

@Getter
public class ExceptionDto {
    @NotNull
    private final Integer code;

    @NotNull private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
