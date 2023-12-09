package org.naemansan.courseapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record CourseUpdateStatusDto(
        @NotNull
        Boolean isEnrolled
) {
}
