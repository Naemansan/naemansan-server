package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.UserServicePort;
import org.naemansan.courseapi.dto.common.UserNameDto;
import org.naemansan.courseapi.utility.ClientUtil;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {
    private final ClientUtil clientUtil;

    @Override
    public List<UserNameDto> findUserNames(List<String> userIds) {
        return clientUtil.getUserNames(userIds);
    }

    @Override
    public UserNameDto findUserName(String userId) {
        return clientUtil.getUserName(userId);
    }
}
