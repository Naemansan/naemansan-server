package org.naemansan.courseapi.application.service;

import lombok.RequiredArgsConstructor;
import org.naemansan.courseapi.application.port.in.query.ReadCourseDependenceCommand;
import org.naemansan.courseapi.application.port.in.usecase.SpotUseCase;
import org.naemansan.courseapi.application.port.out.CourseRepositoryPort;
import org.naemansan.courseapi.application.port.out.SpotRepositoryPort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.dto.response.SpotListDto;
import org.naemansan.courseapi.utility.CourseUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotService implements SpotUseCase {
    private final CourseRepositoryPort courseRepositoryPort;
    private final SpotRepositoryPort spotRepositoryPort;

    private final CourseUtil courseUtil;

    @Override
    public List<SpotListDto> findSpots() {
        return null;
    }

    @Override
    public List<SpotListDto> findSpotsByCourseId(ReadCourseDependenceCommand command) {
        Course course = courseRepositoryPort.findCourseById(command.getCourseId());

        return spotRepositoryPort.findByCourse(course).stream()
                .map(spot -> SpotListDto.builder()
                        .name(spot.getTitle())
                        .description(spot.getContent())
                        .location(courseUtil.point2Location(spot.getLocation()))
                        .category(spot.getCategory())
                        .thumbnailUrl(spot.getThumbnailUrl()).build())
                .toList();
    }
}
