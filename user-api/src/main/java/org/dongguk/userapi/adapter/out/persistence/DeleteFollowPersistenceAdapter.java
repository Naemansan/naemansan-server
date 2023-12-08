package org.dongguk.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.FollowRepository;
import org.dongguk.userapi.application.port.out.DeleteFollowPort;
import org.dongguk.userapi.domain.Follow;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.dto.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
public class DeleteFollowPersistenceAdapter implements DeleteFollowPort {
    private final FollowRepository followRepository;
    @Override
    @Transactional
    public void deleteFollow(Long followId) {
        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        followRepository.delete(follow);
    }
}
