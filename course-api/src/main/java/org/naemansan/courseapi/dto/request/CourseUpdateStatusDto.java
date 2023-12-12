package org.naemansan.courseapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CourseUpdateStatusDto(
        @JsonProperty("isEnrolled")
        @NotNull
        Boolean isEnrolled
) {
}
