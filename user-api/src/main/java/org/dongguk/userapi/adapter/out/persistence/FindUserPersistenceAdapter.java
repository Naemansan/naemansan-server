package org.dongguk.userapi.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.adapter.out.repository.UserRepository;
import org.dongguk.userapi.application.port.out.FindUserPort;
import org.dongguk.userapi.domain.User;
import org.dongguk.userapi.dto.response.UserDetailDto;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.common.dto.ErrorCode;
import org.naemansan.common.exception.CommonException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FindUserPersistenceAdapter implements FindUserPort {
    private final UserRepository userRepository;

    @Override
    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Override
    public User findUserDetailByUuid(String uuid) {
        return userRepository.findUserDetailByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Override
    public UserRepository.UserName findUserNameByUuid(String uuid) {
        return userRepository.findUserNameByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Override
    public List<UserRepository.UserName> findUserNamesByUuids(List<String> uuids) {
        return userRepository.findUserNamesByUuids(uuids.stream()
                .map(UUID::fromString).toList());
    }
}
