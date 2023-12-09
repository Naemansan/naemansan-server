package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;

import java.util.List;

public interface FollowRepositoryPort {
    void createFollow(User following, User followed);

    List<Follow> findFollowingByUuid(User user);

    List<Follow> findFollowedByUuid(User user);

    void deleteFollow(Long followId);
}
