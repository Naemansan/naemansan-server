package org.naemansan.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.application.port.out.UserRepositoryPort;
import org.naemansan.userapi.domain.User;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {
    private final UserRepository userRepository;

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Override
    public UserRepository.UserName findUserNameById(String userId) {
        return userRepository.findUserNameById(UUID.fromString(userId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Override
    public List<UserRepository.UserName> findUserNamesByIds(List<String> userIds) {
        return userRepository.findUserNamesByIds(userIds.stream()
                .map(UUID::fromString)
                .toList());
    }
}
