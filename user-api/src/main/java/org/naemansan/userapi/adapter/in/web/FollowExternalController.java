package org.naemansan.userapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.application.port.in.command.CreateFollowCommand;
import org.naemansan.userapi.application.port.in.command.DeleteFollowCommand;
import org.naemansan.userapi.application.port.in.usecase.FollowRequestUseCase;
import org.naemansan.userapi.dto.request.FollowDto;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowExternalController {
    private final FollowRequestUseCase followRequestUseCase;

    @PostMapping("")
    public ResponseDto<?> createFollow(
            @RequestBody FollowDto followDto
    ) {
        String uuid = "524c1de9-00e2-4ff7-bea4-8b4323413fbf";

        followRequestUseCase.createFollow(CreateFollowCommand.builder()
                .followingUuid(uuid)
                .followedUuid(followDto.followedUuid())
                .build());

        return ResponseDto.ok(null);
    }

    @DeleteMapping("/{followId}")
    public ResponseDto<?> deleteFollow(
            @PathVariable("followId") Long followId
    ) {
        followRequestUseCase.deleteFollow(DeleteFollowCommand.builder()
                .followId(followId)
                .build());

        return ResponseDto.ok(null);
    }
}
