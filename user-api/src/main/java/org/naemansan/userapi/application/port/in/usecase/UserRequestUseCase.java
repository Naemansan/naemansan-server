package org.naemansan.userapi.application.port.in.usecase;


import org.naemansan.userapi.application.port.in.query.FindUserQuery;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.dto.response.UserDetailDto;
import org.naemansan.userapi.dto.response.UserNameDto;

import java.util.List;

public interface UserRequestUseCase {
    UserDetailDto findUserDetailByUuid(FindUserQuery command);

    void updateUserByUuid(UpdateUserCommand command);

    UserNameDto findUserNameByUuid(String uuid);

    List<UserNameDto> findUserNamesByUuids(List<String> uuids);
}