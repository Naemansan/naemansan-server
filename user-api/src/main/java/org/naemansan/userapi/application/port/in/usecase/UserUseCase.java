package org.naemansan.userapi.application.port.in.usecase;


import org.naemansan.userapi.application.port.in.query.ReadUserQuery;
import org.naemansan.userapi.application.port.in.command.UpdateUserCommand;
import org.naemansan.userapi.dto.response.UserDetailDto;
import org.naemansan.userapi.dto.response.UserNameDto;

import java.util.List;
import java.util.Map;

public interface UserUseCase {
    UserDetailDto findUserDetailById(ReadUserQuery command);

    Map<String, Object> updateUserById(UpdateUserCommand command);

    UserNameDto findUserNameById(String uuid);

    List<UserNameDto> findUserNamesByIds(List<String> uuids);
}
