package org.naemansan.userapi.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.application.port.in.query.ReadUserDependenceQuery;
import org.naemansan.userapi.application.port.in.query.ReadUserQuery;
import org.naemansan.userapi.application.port.in.usecase.CourseUseCase;
import org.naemansan.userapi.application.port.in.usecase.FollowUseCase;
import org.naemansan.userapi.application.port.in.usecase.UserUseCase;
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
    private final UserUseCase userUseCase;
    private final FollowUseCase followUseCase;
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
                .build();
    }

    @GetMapping("")
    public ResponseDto<?> readUser() {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        return ResponseDto.ok(userUseCase.findUserDetailByUuid(ReadUserQuery.of(uuid)));
    }

    @GetMapping("/{userId}")
    public ResponseDto<?> readAntherUser(
            @PathVariable("userId") String userUuid
    ) {
        return ResponseDto.ok(userUseCase.findUserDetailByUuid(ReadUserQuery.of(userUuid)));
    }

    @PutMapping("")
    public ResponseDto<?> updateUser(
            @RequestPart(value = "body", required = false) @Valid UserUpdateDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile imgFile
    ) {
        userUseCase.updateUserByUuid(UpdateUserCommand.of(
                "625ad265-cc31-44fd-b783-e8cd047b6903",
                requestDto.nickname(),
                requestDto.introduction(),
                requestDto.tags(),
                imgFile
        ));

        return ResponseDto.ok("updateUserInfo");
    }

    @GetMapping("/followings")
    public ResponseDto<?> readFollowings(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        if (page < 0 || size < 0) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER);
        }

        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";
        return ResponseDto.ok(followUseCase.findFollowingByUserId(ReadUserDependenceQuery.builder()
                .userId(userId)
                .page(page)
                .size(size)
                .build()));
    }

    @GetMapping("/followers")
    public ResponseDto<?> readFollowers(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        if (page < 0 || size < 0) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER);
        }
        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";
        return ResponseDto.ok(followUseCase.findFollowerByUserId(ReadUserDependenceQuery.builder()
                .userId(userId)
                .page(page)
                .size(size)
                .build()));
    }

    @GetMapping("/courses")
    public ResponseDto<?> readCourses(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        if (page < 0 || size < 0) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER);
        }
        String userId = "625ad265-cc31-44fd-b783-e8cd047b6903";
        return ResponseDto.ok();
    }
}
