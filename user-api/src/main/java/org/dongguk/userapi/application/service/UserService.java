package org.dongguk.userapi.application.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.UserRepository;
import org.dongguk.userapi.application.port.in.command.UpdateUserCommand;
import org.dongguk.userapi.application.port.in.query.FindUserQuery;
import org.dongguk.userapi.application.port.in.usecase.UserRequestUseCase;
import org.dongguk.userapi.application.port.out.FindFollowPort;
import org.dongguk.userapi.application.port.out.FindTagNamesPort;
import org.dongguk.userapi.application.port.out.FindUserPort;
import org.dongguk.userapi.domain.Follow;
import org.dongguk.userapi.domain.User;
import org.dongguk.userapi.domain.UserTag;
import org.dongguk.userapi.dto.response.TagDto;
import org.dongguk.userapi.dto.response.UserDetailDto;
import org.dongguk.userapi.dto.response.FollowListDto;
import org.dongguk.userapi.dto.response.UserNameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserRequestUseCase {
    private final FindUserPort findUserPort;
    private final FindTagNamesPort findTagNamesPort;
    private final FindFollowPort findFollowPort;

    @Override
    public UserDetailDto findUserDetailByUuid(FindUserQuery command) {
        User user = findUserPort.findUserDetailByUuid(command.getUuid());

        List<TagDto> tags = new ArrayList<>();
        if (user.getTags() != null && !user.getTags().isEmpty()) {
            tags.addAll(findTagNamesPort.findAll(
                    user.getTags().stream()
                            .map(UserTag::getTagId).toList()));
        }

        List<Follow> followings = findFollowPort.findFollowingByUuid(user);
        List<Follow> followers = findFollowPort.findFollowedByUuid(user);

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
    public void updateUserByUuid(UpdateUserCommand command) {

    }

    @Override
    public UserNameDto findUserNameByUuid(String uuid) {
        UserRepository.UserName userName = findUserPort.findUserNameByUuid(uuid);

        return UserNameDto.builder()
                .uuid(userName.getUuid().toString())
                .nickname(userName.getNickname())
                .profileImageUrl(userName.getProfileImageUrl())
                .build();
    }

    @Override
    public List<UserNameDto> findUserNamesByUuids(List<String> uuids) {
        List<UserRepository.UserName> userNames = findUserPort.findUserNamesByUuids(uuids);

        return userNames.stream()
                .map(userName -> UserNameDto.builder()
                        .uuid(userName.getUuid().toString())
                        .nickname(userName.getNickname())
                        .profileImageUrl(userName.getProfileImageUrl())
                        .build())
                .toList();
    }
}
