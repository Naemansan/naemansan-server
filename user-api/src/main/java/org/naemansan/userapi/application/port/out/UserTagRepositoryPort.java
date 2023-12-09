package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.domain.User;
import org.naemansan.userapi.domain.UserTag;

import java.util.List;

public interface UserTagRepositoryPort {
    List<UserTag> saveUserTags(List<Long> tagIds, User user);

    void deleteUserTags(List<UserTag> userTags);
}
