package org.naemansan.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageInfo(
        @JsonProperty("currentPage") Integer currentPage,
        @JsonProperty("currentSize") Integer size,
        @JsonProperty("totalPage") Integer totalPage,
        @JsonProperty("totalElement") Integer totalElement) {

    public static PageInfo fromPage(Page page) {
        return PageInfo.builder()
                .currentPage(page.getNumber())
                .totalPage(page.getTotalPages())
                .totalElement((int) page.getTotalElements())
                .size(page.getSize())
                .build();
    }
}
