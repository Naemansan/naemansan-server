package org.naemansan.userapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.application.port.in.query.FindUserQuery;
import org.naemansan.userapi.application.port.in.usecase.UserRequestUseCase;
import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;
import org.naemansan.userapi.domain.UserTag;
import org.naemansan.userapi.dto.response.TagDto;
import org.naemansan.userapi.dto.response.UserDetailDto;
import org.naemansan.userapi.dto.response.FollowListDto;
import org.naemansan.userapi.dto.response.UserNameDto;
import org.naemansan.userapi.application.port.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserRequestUseCase {
    private final TagServicePort tagServicePort;
    private final ImageServicePort imageServicePort;

    private final UserRepositoryPort userRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;
    private final UserTagRepositoryPort userTagRepositoryPort;

    @Override
    public UserDetailDto findUserDetailByUuid(FindUserQuery command) {
        User user = userRepositoryPort.findUserDetailByUuid(command.getUuid());

        List<TagDto> tags = new ArrayList<>();
        if (user.getTags() != null && !user.getTags().isEmpty()) {
            tags.addAll(tagServicePort.findByTagDtoIds(
                    user.getTags().stream()
                            .map(UserTag::getTagId)
                            .toList()));
        }

        List<Follow> followings = followRepositoryPort.findFollowingByUuid(user);
        List<Follow> followers = followRepositoryPort.findFollowedByUuid(user);

        return UserDetailDto.builder()
                .uuid(user.getUuid().toString())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .profileImageUrl(user.getProfileImageUrl())
                .tags(tags)
                .followings(followings.stream()
                        .map(follow -> FollowListDto.builder()
                                .id(follow.getId())
                                .userUuid(follow.getFollowed().getUuid().toString())
                                .nickname(follow.getFollowed().getNickname())
                                .profileImageUrl(follow.getFollowed().getProfileImageUrl())
                                .build())
                        .toList())
                .followers(followers.stream()
                        .map(follow -> FollowListDto.builder()
                                .id(follow.getId())
                                .userUuid(follow.getFollowing().getUuid().toString())
                                .nickname(follow.getFollowing().getNickname())
                                .profileImageUrl(follow.getFollowing().getProfileImageUrl())
                                .build())
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public void updateUserByUuid(UpdateUserCommand command) {
        // User 조회
        User user = userRepositoryPort.findUserDetailByUuid(command.getUuid());

        // 이미지 저장(구현 미완)
        String profileImageUrl = imageServicePort.uploadImage(command.getProfileImage());

        // Tag 삭제
        userTagRepositoryPort.deleteUserTags(user.getTags());

        // User Update And Save Tags
        userTagRepositoryPort.saveUserTags(command.getTagIds(), user);
        user.update(command.getNickname(), command.getIntroduction(), profileImageUrl);
    }

    @Override
    public UserNameDto findUserNameByUuid(String uuid) {
        UserRepository.UserName userName = userRepositoryPort.findUserNameByUuid(uuid);

        return UserNameDto.builder()
                .uuid(userName.getUuid().toString())
                .nickname(userName.getNickname())
                .profileImageUrl(userName.getProfileImageUrl())
                .build();
    }

    @Override
    public List<UserNameDto> findUserNamesByUuids(List<String> uuids) {
        List<UserRepository.UserName> userNames = userRepositoryPort.findUserNamesByUuids(uuids);

        return userNames.stream()
                .map(userName -> UserNameDto.builder()
                        .uuid(userName.getUuid().toString())
                        .nickname(userName.getNickname())
                        .profileImageUrl(userName.getProfileImageUrl())
                        .build())
                .toList();
    }
}
