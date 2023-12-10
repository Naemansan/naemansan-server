package org.naemansan.userapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.application.port.in.usecase.UserUseCase;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-users")
public class UserInternalAdapter {
    private final UserUseCase userUseCase;

    @GetMapping("/name")
    public ResponseDto<?> readUserNames(
            @RequestParam("userIds")List<String> userIds
    ) {
        return ResponseDto.ok(userUseCase.findUserNamesByUuids(userIds));
    }

    @GetMapping("/{userId}/name")
    public ResponseDto<?> readUserNames(
            @PathVariable("userId") String userId
    ) {
        return ResponseDto.ok(userUseCase.findUserNameByUuid(userId));
    }
}
