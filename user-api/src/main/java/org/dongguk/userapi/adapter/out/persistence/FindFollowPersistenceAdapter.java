package org.dongguk.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.FollowRepository;
import org.dongguk.userapi.application.port.out.FindFollowPort;
import org.dongguk.userapi.domain.Follow;
import org.dongguk.userapi.domain.User;
import org.naemansan.common.annotaion.PersistenceAdapter;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class FindFollowPersistenceAdapter implements FindFollowPort {
    private final FollowRepository followRepository;

    @Override
    public List<Follow> findFollowingByUuid(User user) {
        return followRepository.findByFollowing(user);
    }

    @Override
    public List<Follow> findFollowedByUuid(User user) {
        return followRepository.findByFollowed(user);
    }
}
