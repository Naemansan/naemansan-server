package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.domain.Follow;
import org.dongguk.userapi.domain.User;

import java.util.List;

public interface FindFollowPort {
    List<Follow> findFollowingByUuid(User user);

    List<Follow> findFollowedByUuid(User user);
}
