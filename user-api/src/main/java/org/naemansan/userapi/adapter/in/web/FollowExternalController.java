package org.naemansan.userapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.application.port.in.command.CreateFollowCommand;
import org.naemansan.userapi.application.port.in.command.DeleteFollowCommand;
import org.naemansan.userapi.application.port.in.usecase.FollowUseCase;
import org.naemansan.userapi.dto.request.FollowDto;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowExternalController {
    private final FollowUseCase followUseCase;

    @PostMapping("")
    public ResponseDto<?> createFollow(
            @RequestBody FollowDto followDto
    ) {
        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";

        followUseCase.createFollow(CreateFollowCommand.builder()
                .followingId(userId)
                .followerId(followDto.userId())
                .build());

        return ResponseDto.ok(null);
    }

    @DeleteMapping("")
    public ResponseDto<?> deleteFollow(
            @RequestBody FollowDto followDto
    ) {
        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";

        followUseCase.deleteFollow(DeleteFollowCommand.builder()
                .followingId(userId)
                .followerId(followDto.userId())
                .build());

        return ResponseDto.ok(null);
    }
}
