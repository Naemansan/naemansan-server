package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.domain.User;

public interface CreateFollowPort {
    void createFollow(User following, User followed);
}
