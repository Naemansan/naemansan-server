package org.naemansan.userapi.application.port.in.usecase;


import org.naemansan.userapi.application.port.in.query.ReadUserQuery;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.dto.response.UserDetailDto;
import org.naemansan.userapi.dto.response.UserNameDto;

import java.util.List;

public interface UserUseCase {
    UserDetailDto findUserDetailByUuid(ReadUserQuery command);

    void updateUserByUuid(UpdateUserCommand command);

    UserNameDto findUserNameByUuid(String uuid);

    List<UserNameDto> findUserNamesByUuids(List<String> uuids);
}
