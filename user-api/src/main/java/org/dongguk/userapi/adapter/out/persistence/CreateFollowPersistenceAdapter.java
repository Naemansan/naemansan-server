package org.dongguk.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.FollowRepository;
import org.dongguk.userapi.application.port.out.CreateFollowPort;
import org.dongguk.userapi.domain.Follow;
import org.dongguk.userapi.domain.User;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.dto.ErrorCode;
import org.naemansan.common.exception.CommonException;

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateFollowPersistenceAdapter implements CreateFollowPort {
    private final FollowRepository followRepository;

    @Override
    public void createFollow(User following, User followed) {
        followRepository.findByFollowingAndFollowed(following, followed)
                .ifPresent(follow -> {
                    throw new CommonException(ErrorCode.DUPLICATED_RESOURCE);
                });

        followRepository.save(Follow.builder()
                .following(following)
                .followed(followed).build());

    }
}
