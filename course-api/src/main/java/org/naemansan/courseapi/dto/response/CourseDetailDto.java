package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.naemansan.courseapi.dto.common.LocationDto;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CourseDetailDto(
        @JsonProperty("id")
        Long id,

        @JsonProperty("title")
        String title,

        @JsonProperty("content")
        String content,
        @JsonProperty("siDongGu")
        String startLocationName,

        @JsonProperty("locations")
        List<LocationDto> locations,

        @JsonProperty("tags")
        List<String> tags,

        @JsonProperty("distance")
        String distance,

        @JsonProperty("createdAt")
        LocalDate createdAt,

        @JsonProperty("userId")
        String userId,

        @JsonProperty("userNickName")
        String userNickName,

        @JsonProperty("userProfileImageUrl")
        String userProfileImageUrl,

        @JsonProperty("isLike")
        Boolean isLike,

        @JsonProperty("likeCount")
        Long likeCount
) {
}
