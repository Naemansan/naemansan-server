package org.naemansan.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.userapi.adapter.out.repository.FollowRepository;
import org.naemansan.userapi.application.port.out.FollowRepositoryPort;
import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@PersistenceAdapter
@RequiredArgsConstructor
public class FollowPersistenceAdapter implements FollowRepositoryPort {
    private final FollowRepository followRepository;

    @Override
    public void createFollow(User following, User follower) {
        followRepository.findByFollowingAndFollower(following, follower)
                .ifPresent(follow -> {
                    throw new CommonException(ErrorCode.DUPLICATED_RESOURCE);
                });

        followRepository.save(Follow.builder()
                .following(following)
                .follower(follower).build());
    }

    @Override
    public void deleteFollow(Follow follow) {
        followRepository.delete(follow);
    }

    @Override
    public Long countFollowingByUser(User user) {
        return followRepository.countByFollowing(user);
    }

    @Override
    public Long countFollowerByUser(User user) {
        return followRepository.countByFollower(user);
    }

    @Override
    public Follow findByFollowingAndFollower(User following, User follower) {
        return followRepository.findByFollowingAndFollower(following, follower)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Override
    public Page<Follow> findFollowingByUser(User user, Pageable pageable) {
        return followRepository.findByFollowing(user, pageable);
    }

    @Override
    public Page<Follow> findFollowerByUser(User user, Pageable pageable) {
        return followRepository.findByFollower(user, pageable);
    }
}
