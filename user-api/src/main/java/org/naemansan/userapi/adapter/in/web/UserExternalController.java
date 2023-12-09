package org.naemansan.userapi.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.application.port.in.query.FindUserQuery;
import org.naemansan.userapi.application.port.in.usecase.UserRequestUseCase;
import org.naemansan.userapi.domain.User;
import org.naemansan.userapi.dto.request.UserUpdateDto;
import org.naemansan.userapi.dto.response.UserDetailDto;
import org.naemansan.userapi.dto.type.EProvider;
import org.naemansan.userapi.dto.type.ERole;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserExternalController {
    private final UserRequestUseCase userRequestUseCase;
    private final UserRepository userRepository;

    @PostMapping("")
    public UserDetailDto createUser() {
        User user = userRepository.save(User.builder()
                .serialId("test")
                .password("test")
                .provider(EProvider.DEFAULT)
                .role(ERole.USER)
                .build());
        return UserDetailDto.builder()
                .uuid(user.getUuid().toString())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .profileImageUrl(user.getProfileImageUrl())
                .tags(List.of())
                .followings(List.of())
                .followers(List.of())
                .build();
    }

    @GetMapping("")
    public ResponseDto<?> readUser() {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        return ResponseDto.ok(userRequestUseCase.findUserDetailByUuid(FindUserQuery.of(uuid)));
    }

    @GetMapping("/{userUuid}")
    public ResponseDto<?> readAntherUser(
            @PathVariable("userUuid") String userUuid
    ) {
        return ResponseDto.ok(userRequestUseCase.findUserDetailByUuid(FindUserQuery.of(userUuid)));
    }

    @PutMapping("")
    public ResponseDto<?> updateUser(
            @RequestPart(value = "body", required = false) @Valid UserUpdateDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile imgFile
    ) {
        userRequestUseCase.updateUserByUuid(UpdateUserCommand.of(
                "625ad265-cc31-44fd-b783-e8cd047b6903",
                requestDto.nickname(),
                requestDto.introduction(),
                requestDto.tags(),
                imgFile
        ));

        return ResponseDto.ok("updateUserInfo");
    }
}
