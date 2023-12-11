package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.dto.response.PageInfo;
import org.naemansan.courseapi.application.port.in.command.CreateMomentCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseDependenceCommand;
import org.naemansan.courseapi.application.port.in.query.ReadMomentsCommand;
import org.naemansan.courseapi.application.port.in.usecase.MomentUseCase;
import org.naemansan.courseapi.application.port.out.*;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Moment;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.persistent.UserNamePersistent;
import org.naemansan.courseapi.dto.response.MomentListDto;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.dto.type.EWeather;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MomentService implements MomentUseCase {
    private final CourseRepositoryPort courseRepositoryPort;
    private final MomentRepositoryPort momentRepositoryPort;

    private final UserServicePort userServicePort;
    private final WeatherServicePort weatherServicePort;
    private final EmotionServicePort emotionServicePort;

    @Override
    public MomentListDto createMoment(CreateMomentCommand command) {
        // 유저 존재 확인
        UserNamePersistent userName = userServicePort.findUserName(command.getUserId());
        // 코스 존재 확인
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        // 입력 정보 분석
        LocationDto location = CourseUtil.multiPoint2Locations(course.getLocations()).get(0);
        EEmotion emotion = emotionServicePort.analyzeContent(command.getContent());
        EWeather weather = weatherServicePort.getCurrentWeather(location);

        // 모멘트 생성
        Moment moment = momentRepositoryPort.createMoment(
                command.getUserId(),
                course,
                command.getContent(),
                emotion,
                weather
        );

        // 결과 반환
        return MomentListDto.builder()
                .id(moment.getId())
                .courseId(moment.getCourse().getId())
                .courseTitle(moment.getCourse().getTitle())
                .nickname(userName.nickname())
                .content(moment.getContent())
                .createdAt(moment.getCreatedAt().toString())
                .emotion(moment.getEmotion())
                .weather(moment.getWeather())
                .build();
    }

    @Override
    public Map<String, Object> findMoments(ReadMomentsCommand command) {
        // 모든 모멘트 조회
        Pageable pageable = PageRequest.of(
                command.getPage(),
                command.getSize(),
                Sort.by("createdAt").descending());

        Page<Moment> moments = momentRepositoryPort.findMoments(pageable);

        // 유저 이름 조회
        List<UserNamePersistent> userNames = userServicePort.findUserNames(
                moments.getContent().stream().map(moment -> moment.getUserId().toString()).toList()
        );

        // 결과 반환
        return Map.of(
                "pageInfo", PageInfo.fromPage(moments),
                "moments", moments.getContent().stream().map(moment -> MomentListDto.builder()
                        .id(moment.getId())
                        .courseId(moment.getCourse().getId())
                        .courseTitle(moment.getCourse().getTitle())
                        .nickname(userNames.stream()
                                .filter(userName -> userName.uuid().equals(moment.getUserId().toString()))
                                .findFirst().orElseThrow().nickname())
                        .content(moment.getContent())
                        .createdAt(moment.getCreatedAt().toString())
                        .emotion(moment.getEmotion())
                        .weather(moment.getWeather())
                        .build()).toList()
        );
    }

    @Override
    public Map<String, Object> findMomentsByCourseId(ReadCourseDependenceCommand command) {
        // 코스 존재 확인
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        // 코스에 속한 모멘트 조회
        Pageable pageable = PageRequest.of(
                command.getPage(),
                command.getSize(),
                Sort.by("createdAt").descending());

        Page<Moment> moments = momentRepositoryPort.findMomentsByCourse(course, pageable);

        // 유저 이름 조회
        List<UserNamePersistent> userNames = userServicePort.findUserNames(
                moments.getContent().stream().map(moment -> moment.getUserId().toString()).toList()
        );

        // 결과 반환
        return Map.of(
                "pageInfo", PageInfo.fromPage(moments),
                "moments", moments.getContent().stream().map(moment -> MomentListDto.builder()
                        .id(moment.getId())
                        .courseId(moment.getCourse().getId())
                        .courseTitle(moment.getCourse().getTitle())
                        .nickname(userNames.stream()
                                .filter(userName -> userName.uuid().equals(moment.getUserId().toString()))
                                .findFirst().orElseThrow().nickname())
                        .content(moment.getContent())
                        .createdAt(moment.getCreatedAt().toString())
                        .emotion(moment.getEmotion())
                        .weather(moment.getWeather())
                        .build()).toList()
        );
    }
}
