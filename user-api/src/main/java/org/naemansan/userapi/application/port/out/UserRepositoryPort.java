package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.domain.User;

import java.util.List;

public interface UserRepositoryPort {
    User findUserByUuid(String uuid);

    User findUserDetailByUuid(String uuid);

    UserRepository.UserName findUserNameByUuid(String uuid);

    List<UserRepository.UserName> findUserNamesByUuids(List<String> uuids);
}
