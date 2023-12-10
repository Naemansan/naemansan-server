package org.naemansan.userapi.application.port.in.usecase;

import org.naemansan.userapi.application.port.in.command.CreateFollowCommand;
import org.naemansan.userapi.application.port.in.command.DeleteFollowCommand;
import org.naemansan.userapi.application.port.in.query.ReadUserDependenceQuery;

import java.util.Map;

public interface FollowUseCase {
    void createFollow(CreateFollowCommand command);

    void deleteFollow(DeleteFollowCommand command);

    Map<String, Object> findFollowingByUserId(ReadUserDependenceQuery command);

    Map<String, Object> findFollowerByUserId(ReadUserDependenceQuery command);
}
