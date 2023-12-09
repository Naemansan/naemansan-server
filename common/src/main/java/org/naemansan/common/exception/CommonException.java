package org.naemansan.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}

