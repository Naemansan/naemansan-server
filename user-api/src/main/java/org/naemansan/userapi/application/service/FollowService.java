package org.naemansan.userapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.response.PageInfo;
import org.naemansan.userapi.application.port.in.command.CreateFollowCommand;
import org.naemansan.userapi.application.port.in.command.DeleteFollowCommand;
import org.naemansan.userapi.application.port.in.query.ReadUserDependenceQuery;
import org.naemansan.userapi.application.port.in.usecase.FollowUseCase;
import org.naemansan.userapi.application.port.out.FollowRepositoryPort;
import org.naemansan.userapi.application.port.out.UserRepositoryPort;
import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;
import org.naemansan.userapi.dto.response.FollowListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;

    @Override
    public void createFollow(CreateFollowCommand command) {
        User following = userRepositoryPort.findUserByUuid(command.getFollowingId());
        User follower = userRepositoryPort.findUserByUuid(command.getFollowerId());

        followRepositoryPort.createFollow(following, follower);
    }

    @Override
    @Transactional
    public void deleteFollow(DeleteFollowCommand command) {
        User following = userRepositoryPort.findUserByUuid(command.getFollowingId());
        User follower = userRepositoryPort.findUserByUuid(command.getFollowerId());

        Follow follow = followRepositoryPort.findByFollowingAndFollower(following, follower);

        followRepositoryPort.deleteFollow(follow);
    }

    @Override
    public Map<String, Object> findFollowingByUserId(ReadUserDependenceQuery command) {
        // 유저 조회
        User user = userRepositoryPort.findUserByUuid(command.getUserId());

        // Following 조회
        Pageable pageable = PageRequest.of(command.getPage(), command.getSize());
        Page<Follow> following = followRepositoryPort.findFollowingByUser(user, pageable);

        // 반환
        return Map.of(
                "pageInfo", PageInfo.fromPage(following),
                "followings", following.getContent().stream()
                        .map(follow -> FollowListDto.builder()
                                .userId(follow.getFollowed().getUuid().toString())
                                .nickname(follow.getFollowed().getNickname())
                                .introduction(follow.getFollowed().getIntroduction())
                                .profileImageUrl(follow.getFollowed().getProfileImageUrl())
                                .build())
                        .toList()
        );
    }

    @Override
    public Map<String, Object> findFollowerByUserId(ReadUserDependenceQuery command) {
        // 유저 조회
        User user = userRepositoryPort.findUserByUuid(command.getUserId());

        // Follower 조회
        Pageable pageable = PageRequest.of(command.getPage(), command.getSize());
        Page<Follow> Follower = followRepositoryPort.findFollowerByUser(user, pageable);

        return Map.of(
                "pageInfo", PageInfo.fromPage(Follower),
                "followers", Follower.getContent().stream()
                        .map(follow -> FollowListDto.builder()
                                .userId(follow.getFollowing().getUuid().toString())
                                .nickname(follow.getFollowing().getNickname())
                                .introduction(follow.getFollowing().getIntroduction())
                                .profileImageUrl(follow.getFollowing().getProfileImageUrl())
                                .build())
                        .toList()
        );
    }
}
