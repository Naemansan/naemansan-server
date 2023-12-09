package org.dongguk.userapi.application.port.in.usecase;


import org.dongguk.userapi.application.port.in.query.FindUserQuery;
import org.dongguk.userapi.application.port.in.command.UpdateUserCommand;
import org.dongguk.userapi.dto.response.UserDetailDto;
import org.dongguk.userapi.dto.response.UserNameDto;

import java.util.List;

public interface UserRequestUseCase {
    UserDetailDto findUserDetailByUuid(FindUserQuery command);

    void updateUserByUuid(UpdateUserCommand command);

    UserNameDto findUserNameByUuid(String uuid);

    List<UserNameDto> findUserNamesByUuids(List<String> uuids);
}
