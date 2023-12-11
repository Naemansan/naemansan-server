package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.persistent.UserNamePersistent;

import java.util.List;

public interface UserServicePort {

    List<UserNamePersistent> findUserNames(List<String> userIds);

    UserNamePersistent findUserName(String userId);
}
