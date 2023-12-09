package org.dongguk.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.FollowRepository;
import org.dongguk.userapi.application.port.out.FollowRepositoryPort;
import org.dongguk.userapi.domain.Follow;
import org.dongguk.userapi.domain.User;
import org.naemansan.common.annotaion.PersistenceAdapter;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class FollowPersistenceAdapter implements FollowRepositoryPort {
    private final FollowRepository followRepository;

    @Override
    public void createFollow(User following, User followed) {
        followRepository.findByFollowingAndFollowed(following, followed)
                .ifPresent(follow -> {
                    throw new RuntimeException();
                });

        followRepository.save(Follow.builder()
                .following(following)
                .followed(followed).build());
    }

    @Override
    public List<Follow> findFollowingByUuid(User user) {
        return followRepository.findByFollowing(user);
    }

    @Override
    public List<Follow> findFollowedByUuid(User user) {
        return followRepository.findByFollowed(user);
    }

    @Override
    public void deleteFollow(Long followId) {
        followRepository.deleteById(followId);
    }
}
