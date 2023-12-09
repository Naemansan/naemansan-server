package org.dongguk.userapi.application.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.application.port.in.command.CreateFollowCommand;
import org.dongguk.userapi.application.port.in.command.DeleteFollowCommand;
import org.dongguk.userapi.application.port.in.usecase.FollowRequestUseCase;
import org.dongguk.userapi.application.port.out.FollowRepositoryPort;
import org.dongguk.userapi.application.port.out.UserRepositoryPort;
import org.dongguk.userapi.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowRequestUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;

    @Override
    public void createFollow(CreateFollowCommand command) {
        User following = userRepositoryPort.findUserByUuid(command.getFollowingUuid());
        User followed = userRepositoryPort.findUserByUuid(command.getFollowedUuid());

        followRepositoryPort.createFollow(following, followed);
    }

    @Override
    public void deleteFollow(DeleteFollowCommand command) {
        followRepositoryPort.deleteFollow(command.getFollowId());
    }
}
