package org.naemansan.courseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.common.TagDto;

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
        @JsonProperty("startLocationName")
        String startLocationName,

        @JsonProperty("locations")
        List<LocationDto> locations,

        @JsonProperty("tags")
        List<TagDto> tags,

        @JsonProperty("distance")
        String distance,

        @JsonProperty("createdAt")
        String createdAt,

        @JsonProperty("likeCount")
        Long likeCount,

        @JsonProperty("userId")
        String userId,

        @JsonProperty("userNickName")
        String userNickName,

        @JsonProperty("userProfileImageUrl")
        String userProfileImageUrl
) {
}
