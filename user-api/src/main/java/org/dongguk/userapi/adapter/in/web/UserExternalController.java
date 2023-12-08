package org.dongguk.userapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.UserRepository;
import org.dongguk.userapi.application.port.in.query.FindUserQuery;
import org.dongguk.userapi.application.port.in.usecase.UserRequestUseCase;
import org.dongguk.userapi.domain.User;
import org.dongguk.userapi.dto.response.UserDetailDto;
import org.dongguk.userapi.dto.type.EProvider;
import org.dongguk.userapi.dto.type.ERole;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserExternalController {
    private final UserRequestUseCase userRequestUseCase;
    private final UserRepository userRepository;

    @PostMapping("")
    public UserDetailDto createUserInfo() {
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
    public ResponseDto<?> readUserInfo() {
        String uuid = "524c1de9-00e2-4ff7-bea4-8b4323413fbf";
        return ResponseDto.ok(userRequestUseCase.findUserDetailByUuid(FindUserQuery.of(uuid)));
    }

    @GetMapping("/{userUuid}")
    public ResponseDto<?> hello(
            @PathVariable("userUuid") String userUuid
    ) {
        return ResponseDto.ok(userRequestUseCase.findUserDetailByUuid(FindUserQuery.of(userUuid)));
    }
}
