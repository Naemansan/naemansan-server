package org.dongguk.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.UserTagRepository;
import org.dongguk.userapi.application.port.out.UserTagRepositoryPort;
import org.dongguk.userapi.domain.User;
import org.dongguk.userapi.domain.UserTag;
import org.naemansan.common.annotaion.PersistenceAdapter;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserTagPersistenceAdapter implements UserTagRepositoryPort {
    private final UserTagRepository userTagRepository;

    @Override
    public List<UserTag> saveUserTags(List<Long> tagIds, User user) {
        return userTagRepository.saveAll(
                tagIds.stream()
                        .map(tagId -> UserTag.builder()
                                .tagId(tagId)
                                .user(user)
                                .build())
                        .toList());
    }

    @Override
    public void deleteUserTags(List<UserTag> userTags) {
        userTagRepository.deleteAll(userTags);
        userTagRepository.flush();
    }
}
