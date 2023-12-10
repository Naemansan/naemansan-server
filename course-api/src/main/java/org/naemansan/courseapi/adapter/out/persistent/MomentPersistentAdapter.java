package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.courseapi.adapter.out.repository.MomentRepository;
import org.naemansan.courseapi.application.port.out.MomentRepositoryPort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Moment;
import org.naemansan.courseapi.dto.response.MomentListDto;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.dto.type.EWeather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MomentPersistentAdapter implements MomentRepositoryPort {
    private final MomentRepository momentRepository;

    @Override
    public Moment createMoment(String userId, Course course, String content, EEmotion emotion, EWeather weather) {
        return momentRepository.save(Moment.builder()
                .userId(UUID.fromString(userId))
                .course(course)
                .content(content)
                .emotion(emotion)
                .weather(weather)
                .build());
    }

    @Override
    public Page<Moment> findMoments(Pageable pageable) {
        return momentRepository.findAll(pageable);
    }

    @Override
    public Page<Moment> findMomentsByCourse(Course course, Pageable pageable) {
        return momentRepository.findByCourse(course, pageable);
    }

    @Override
    public Long countByCourse(Course course) {
        return momentRepository.countByCourse(course);
    }

    @Override
    public Map<Long, Long> countByCourses(List<Course> courses) {
        List<MomentRepository.MomentCount> momentCounts = momentRepository.countByCourses(courses);

        Map<Long, Long> result = new HashMap<>();

        momentCounts.forEach(momentCount -> result.put(momentCount.getCourseId(), momentCount.getCount()));

        return result;
    }
}
