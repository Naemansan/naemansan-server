package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.persistent.UserNamePersistent;

import java.util.List;
import java.util.Map;

public interface UserServicePort {

    Map<String, UserNamePersistent> findUserNames(List<String> userIds);

    UserNamePersistent findUserName(String userId);
}
