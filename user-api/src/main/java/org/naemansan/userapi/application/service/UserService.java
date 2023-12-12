package org.naemansan.userapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.application.port.in.query.ReadUserQuery;
import org.naemansan.userapi.application.port.in.usecase.UserUseCase;
import org.naemansan.userapi.domain.User;
import org.naemansan.userapi.domain.UserTag;
import org.naemansan.userapi.dto.response.TagDto;
import org.naemansan.userapi.dto.response.UserDetailDto;
import org.naemansan.userapi.dto.response.UserNameDto;
import org.naemansan.userapi.application.port.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final TagServicePort tagServicePort;
    private final ImageServicePort imageServicePort;
    private final CourseServicePort courseServicePort;
    private final UserRepositoryPort userRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;
    private final UserTagRepositoryPort userTagRepositoryPort;

    @Override
    public UserDetailDto findUserDetailById(ReadUserQuery command) {
        User user = userRepositoryPort.findUserById(command.getUuid());

        List<TagDto> tags = new ArrayList<>();
        if (user.getTags() != null && !user.getTags().isEmpty()) {
            tags.addAll(tagServicePort.findByTagDtoIds(
                    user.getTags().stream()
                            .map(UserTag::getTagId)
                            .toList()));
        }

        return UserDetailDto.builder()
                .id(user.getId().toString())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .profileImageUrl(user.getProfileImageUrl())
                .tags(tags)
                .followingCount(followRepositoryPort.countFollowingByUser(user))
                .followerCount(followRepositoryPort.countFollowerByUser(user))
                .build();
    }

    @Override
    @Transactional
    public Map<String, Object> updateUserById(UpdateUserCommand command) {
        // User 조회
        User user = userRepositoryPort.findUserById(command.getId());

        String profileImageUrl = null;
        String preSignedUrl = null;

        // PreSignedUrl 생성
        if (command.getImageState().isChanged()) {
            Map<String, String> fileUrls = imageServicePort.getUploadImageUrl(command.getImageState().type());
            preSignedUrl = fileUrls.get("preSignedUrl");
            profileImageUrl = fileUrls.get("fileUrl");
        }

        // UserTag Delete After Save
        if (command.getDeletedTagIds() != null && !command.getDeletedTagIds().isEmpty())
            userTagRepositoryPort.deleteUserTags(command.getDeletedTagIds(), user);

        if (command.getCreatedTagIds() != null && !command.getCreatedTagIds().isEmpty())
            userTagRepositoryPort.saveUserTags(command.getCreatedTagIds(), user);

        user.update(command.getNickname(), command.getIntroduction(), profileImageUrl);

        Map<String, Object> result = new HashMap<>();

        result.put("preSignedUrl", preSignedUrl);

        return result;
    }

    @Override
    public UserNameDto findUserNameById(String uuid) {
        UserRepository.UserName userName = userRepositoryPort.findUserNameById(uuid);

        return UserNameDto.builder()
                .id(userName.getId().toString())
                .nickname(userName.getNickname())
                .profileImageUrl(userName.getProfileImageUrl())
                .build();
    }

    @Override
    public List<UserNameDto> findUserNamesByIds(List<String> uuids) {
        List<UserRepository.UserName> userNames = userRepositoryPort.findUserNamesByIds(uuids);

        return userNames.stream()
                .map(userName -> UserNameDto.builder()
                        .id(userName.getId().toString())
                        .nickname(userName.getNickname())
                        .profileImageUrl(userName.getProfileImageUrl())
                        .build())
                .toList();
    }

    @Override
    public Map<String, Object> getCourseList(String filter, Integer page, Integer size) {
        return courseServicePort.getCourseList(filter, page, size);
    }
}
