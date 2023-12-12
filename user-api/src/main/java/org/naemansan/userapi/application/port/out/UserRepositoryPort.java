package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.adapter.out.repository.UserRepository;
import org.naemansan.userapi.domain.User;

import java.util.List;

public interface UserRepositoryPort {
    User findUserById(String userId);

    UserRepository.UserName findUserNameById(String userId);

    List<UserRepository.UserName> findUserNamesByIds(List<String> userIds);
}
