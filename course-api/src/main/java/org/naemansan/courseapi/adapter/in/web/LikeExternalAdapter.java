package org.naemansan.courseapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteLikeCommand;
import org.naemansan.courseapi.application.port.in.usecase.LikeUseCase;
import org.naemansan.courseapi.dto.request.LikeDto;
import org.springframework.web.bind.annotation.*;

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
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        likeUseCase.createLike(CreateLikeCommand.builder()
                .userId(uuid)
                .courseId(likeDto.courseId())
                .build());

        return ResponseDto.ok(null);
    }

    @DeleteMapping("")
    public ResponseDto<?> deleteLike(
            @RequestBody LikeDto likeDto
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        likeUseCase.deleteLike(DeleteLikeCommand.builder()
                .userId(uuid)
                .courseId(likeDto.courseId())
                .build());

        return ResponseDto.ok(null);
    }
}
