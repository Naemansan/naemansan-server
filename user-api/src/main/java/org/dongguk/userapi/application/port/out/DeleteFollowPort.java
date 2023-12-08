package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.domain.User;

public interface DeleteFollowPort {
    void deleteFollow(Long followId);
}
