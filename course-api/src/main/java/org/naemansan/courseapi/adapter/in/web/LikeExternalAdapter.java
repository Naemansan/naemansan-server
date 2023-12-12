package org.naemansan.courseapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteLikeCommand;
import org.naemansan.courseapi.application.port.in.command.ReadLikeStatusCommand;
import org.naemansan.courseapi.application.port.in.command.ReadLikesStatusCommand;
import org.naemansan.courseapi.application.port.in.usecase.LikeUseCase;
import org.naemansan.courseapi.dto.request.LikeDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeExternalAdapter {
    private final LikeUseCase likeUseCase;

    @PostMapping("")
    public ResponseDto<?> createLike(
            @RequestBody LikeDto likeDto
    ) {
        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";
        likeUseCase.createLike(CreateLikeCommand.builder()
                .userId(userId)
                .courseId(likeDto.courseId())
                .build());

        return ResponseDto.created(null);
    }

    @GetMapping("")
    public ResponseDto<?> readLikes(
            @RequestParam(value = "courseIds") List<Long> courseIds
    ) {
        if (courseIds.isEmpty()) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER);
        }

        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";
        if (courseIds.size() == 1) {
            return ResponseDto.ok(likeUseCase.readLikeStatus(ReadLikeStatusCommand.builder()
                    .userId(userId)
                    .courseId(courseIds.get(0))
                    .build()));
        } else{
            return ResponseDto.ok(likeUseCase.readLikesStatus(ReadLikesStatusCommand.builder()
                    .userId(userId)
                    .courseIds(courseIds)
                    .build()));
        }
    }

    @DeleteMapping("")
    public ResponseDto<?> deleteLike(
            @RequestBody LikeDto likeDto
    ) {
        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";
        likeUseCase.deleteLike(DeleteLikeCommand.builder()
                .userId(userId)
                .courseId(likeDto.courseId())
                .build());

        return ResponseDto.ok(null);
    }
}
