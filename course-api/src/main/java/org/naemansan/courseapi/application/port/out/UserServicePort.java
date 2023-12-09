package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.common.UserNameDto;

import java.util.List;

public interface UserServicePort {

    List<UserNameDto> findUserNames(List<String> userIds);

    UserNameDto findUserName(String userId);
}
