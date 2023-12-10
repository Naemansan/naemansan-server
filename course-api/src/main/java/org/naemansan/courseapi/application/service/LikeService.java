package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteLikeCommand;
import org.naemansan.courseapi.application.port.in.usecase.LikeUseCase;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.application.port.out.LikeRepositoryPort;
import org.naemansan.courseapi.application.port.out.UserServicePort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Like;
import org.naemansan.courseapi.dto.common.UserNameDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService implements LikeUseCase {
    private final CourseRepositoryPort courseRepositoryPort;
    private final LikeRepositoryPort likeRepositoryPort;

    private final UserServicePort userServicePort;

    @Override
    public void createLike(CreateLikeCommand command) {
        // 유저 존재 확인
        UserNameDto userNameDto = userServicePort.findUserName(command.getUserId());

        // 산책로 존재 확인
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        // 좋아요 생성
        likeRepositoryPort.createLike(command.getUserId(), course);
    }

    @Override
    @Transactional
    public void deleteLike(DeleteLikeCommand command) {
        // 유저 존재 확인
        UserNameDto userNameDto = userServicePort.findUserName(command.getUserId());

        // 산책로 존재 확인
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        // 좋아요 존재 확인
        Like like = likeRepositoryPort.findLikeByUserIdAndCourse(command.getUserId(), course);

        // 좋아요 삭제
        likeRepositoryPort.deleteLike(like);
    }
}
