package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.courseapi.application.port.in.command.CreateLikeCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteLikeCommand;
import org.naemansan.courseapi.application.port.in.command.ReadLikeStatusCommand;
import org.naemansan.courseapi.application.port.in.command.ReadLikesStatusCommand;
import org.naemansan.courseapi.application.port.in.usecase.LikeUseCase;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.application.port.out.LikeRepositoryPort;
import org.naemansan.courseapi.application.port.out.UserServicePort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Like;
import org.naemansan.courseapi.dto.persistent.UserNamePersistent;
import org.naemansan.courseapi.dto.response.LikeStatusDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService implements LikeUseCase {
    private final CourseRepositoryPort courseRepositoryPort;
    private final LikeRepositoryPort likeRepositoryPort;

    private final UserServicePort userServicePort;

    @Override
    public void createLike(CreateLikeCommand command) {
        // 유저 존재 확인
        UserNamePersistent userNamePersistent = userServicePort.findUserName(command.getUserId());

        // 산책로 존재 확인
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        // 좋아요 생성
        likeRepositoryPort.createLike(command.getUserId(), course);
    }

    @Override
    @Transactional
    public void deleteLike(DeleteLikeCommand command) {
        // 유저 존재 확인
        UserNamePersistent userNamePersistent = userServicePort.findUserName(command.getUserId());

        // 산책로 존재 확인
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        // 좋아요 존재 확인
        Like like = likeRepositoryPort.findLikeByUserIdAndCourse(command.getUserId(), course);

        // 좋아요 삭제
        likeRepositoryPort.deleteLike(like);
    }

    @Override
    public LikeStatusDto readLikeStatus(ReadLikeStatusCommand command) {
        // 유저 존재 확인
        UserNamePersistent userNamePersistent = userServicePort.findUserName(command.getUserId());

        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        Boolean isLiked = likeRepositoryPort.existsByUserIdAndCourse(UUID.fromString(command.getUserId()), course);

        return LikeStatusDto.builder()
                .courseId(course.getId())
                .isLiked(isLiked)
                .build();
    }

    @Override
    public List<LikeStatusDto> readLikesStatus(ReadLikesStatusCommand command) {
        // 유저 존재 확인
        UserNamePersistent userNamePersistent = userServicePort.findUserName(command.getUserId());

        List<Course> courses = courseRepositoryPort.findCoursesByIds(
                command.getCourseIds()).values().stream().toList();


        // 좋아요 존재 확인
        Map<Long, Boolean> result = likeRepositoryPort.existsByUserIdAndCourses(UUID.fromString(command.getUserId()), courses);

        return command.getCourseIds().stream()
                .map(courseId -> LikeStatusDto.builder()
                        .courseId(courseId)
                        .isLiked(result.getOrDefault(courseId, false))
                        .build())
                .toList();
    }
}
