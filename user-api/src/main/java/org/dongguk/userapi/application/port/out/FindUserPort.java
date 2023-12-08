package org.dongguk.userapi.application.port.out;

import org.dongguk.userapi.domain.User;

public interface FindUserPort {
    User findUserByUuid(String uuid);

    User findUserDetailByUuid(String uuid);
}
