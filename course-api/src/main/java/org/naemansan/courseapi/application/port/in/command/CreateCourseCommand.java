package org.naemansan.courseapi.application.port.in.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.request.SpotDto;

import java.util.List;

@Getter
public class CreateCourseCommand extends SelfValidating<CreateCourseCommand> {
    @Size(min = 36, max = 36)
    String userId;

    @Size(min = 1, max = 12)
    String title;

    @Size(min = 1, max = 300)
    String content;

    @NotNull
    List<Long> tagIds;

    @NotNull
    List<LocationDto> locations;

    @NotNull
    List<SpotDto> spots;

    private CreateCourseCommand(
            String userId, String title, String content,
            List<Long> tagIds, List<LocationDto> locations, List<SpotDto> spots) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tagIds = tagIds;
        this.locations = locations;
        this.spots = spots;
        this.validateSelf();
    }

    public static CreateCourseCommand of(
            String userId, String title, String content,
            List<Long> tagIds, List<LocationDto> locations, List<SpotDto> spots) {
        return new CreateCourseCommand(userId, title, content, tagIds, locations, spots);
    }
}
