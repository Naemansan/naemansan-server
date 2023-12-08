package org.dongguk.userapi.application.port.in.usecase;


import org.dongguk.userapi.application.port.in.query.FindUserQuery;
import org.dongguk.userapi.application.port.in.command.UpdateUserCommand;
import org.dongguk.userapi.dto.response.UserDetailDto;

public interface UserRequestUseCase {
    UserDetailDto findUserDetailByUuid(FindUserQuery command);

    void updateUserByUuid(UpdateUserCommand command);
}
