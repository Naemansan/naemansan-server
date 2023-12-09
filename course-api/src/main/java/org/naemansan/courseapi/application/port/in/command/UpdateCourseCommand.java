package org.naemansan.courseapi.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.naemansan.common.SelfValidating;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.request.SpotDto;

import java.util.List;

@Getter
public class UpdateCourseCommand extends SelfValidating<UpdateCourseCommand> {
    @Size(min = 36, max = 36)
    String userId;

    @NotNull
    Long courseId;

    @Size(min = 1, max = 12)
    String title;

    @Size(min = 1, max = 300)
    String content;

    @NotNull
    List<Long> tagIds;

    @NotNull
    List<SpotDto> spots;

    private UpdateCourseCommand(
            String userId, Long courseId, String title, String content,
            List<Long> tagIds, List<SpotDto> spots) {
        this.userId = userId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.tagIds = tagIds;
        this.spots = spots;
        this.validateSelf();
    }

    public static UpdateCourseCommand of(
            String userId, Long courseId, String title, String content,
            List<Long> tagIds, List<SpotDto> spots) {
        return new UpdateCourseCommand(userId, courseId, title, content, tagIds, spots);
    }
}
