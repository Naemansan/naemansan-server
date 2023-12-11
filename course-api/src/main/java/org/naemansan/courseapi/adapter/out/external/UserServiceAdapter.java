package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.UserServicePort;
import org.naemansan.courseapi.dto.persistent.UserNamePersistent;
import org.naemansan.courseapi.utility.InternalClientUtil;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {
    private final InternalClientUtil internalClientUtil;

    @Override
    public List<UserNamePersistent> findUserNames(List<String> userIds) {
        return internalClientUtil.getUserNames(userIds);
    }

    @Override
    public UserNamePersistent findUserName(String userId) {
        return internalClientUtil.getUserName(userId);
    }
}
