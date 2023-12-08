package org.dongguk.userapi.application.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.application.port.in.command.CreateFollowCommand;
import org.dongguk.userapi.application.port.in.command.DeleteFollowCommand;
import org.dongguk.userapi.application.port.in.usecase.FollowRequestUseCase;
import org.dongguk.userapi.application.port.out.CreateFollowPort;
import org.dongguk.userapi.application.port.out.DeleteFollowPort;
import org.dongguk.userapi.application.port.out.FindUserPort;
import org.dongguk.userapi.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowRequestUseCase {
    private final FindUserPort findUserPort;
    private final CreateFollowPort createFollowPort;
    private final DeleteFollowPort deleteFollowPort;

    @Override
    public void createFollow(CreateFollowCommand command) {
        User following = findUserPort.findUserByUuid(command.getFollowingUuid());
        User followed = findUserPort.findUserByUuid(command.getFollowedUuid());

        createFollowPort.createFollow(following, followed);
    }

    @Override
    public void deleteFollow(DeleteFollowCommand command) {
        deleteFollowPort.deleteFollow(command.getFollowId());
    }
}
