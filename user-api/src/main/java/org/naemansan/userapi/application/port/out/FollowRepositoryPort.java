package org.naemansan.userapi.application.port.out;

import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowRepositoryPort {
    void createFollow(User following, User followed);

    void deleteFollow(Follow follow);

    Long countFollowingByUser(User user);

    Long countFollowerByUser(User user);

    Follow findByFollowingAndFollower(User following, User follower);

    Page<Follow> findFollowingByUser(User user, Pageable pageable);

    Page<Follow> findFollowerByUser(User user, Pageable pageable);
}
