package org.dongguk.userapi.application.port.in.usecase;

import org.dongguk.userapi.application.port.in.command.CreateFollowCommand;
import org.dongguk.userapi.application.port.in.command.DeleteFollowCommand;

public interface FollowRequestUseCase {
    void createFollow(CreateFollowCommand command);

    void deleteFollow(DeleteFollowCommand command);
}
