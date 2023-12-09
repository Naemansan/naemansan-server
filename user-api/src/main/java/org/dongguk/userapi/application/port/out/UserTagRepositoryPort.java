package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.domain.User;
import org.dongguk.userapi.domain.UserTag;

import java.util.List;

public interface UserTagRepositoryPort {
    List<UserTag> saveUserTags(List<Long> tagIds, User user);

    void deleteUserTags(List<UserTag> userTags);
}
