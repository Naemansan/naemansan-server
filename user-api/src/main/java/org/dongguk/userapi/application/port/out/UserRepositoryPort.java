package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.adapter.out.repository.UserRepository;
import org.dongguk.userapi.domain.User;

import java.util.List;

public interface UserRepositoryPort {
    User findUserByUuid(String uuid);

    User findUserDetailByUuid(String uuid);

    UserRepository.UserName findUserNameByUuid(String uuid);

    List<UserRepository.UserName> findUserNamesByUuids(List<String> uuids);
}
