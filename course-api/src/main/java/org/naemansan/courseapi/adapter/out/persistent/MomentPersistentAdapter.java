package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.courseapi.adapter.out.repository.MomentRepository;
import org.naemansan.courseapi.application.port.out.MomentRepositoryPort;
import org.naemansan.courseapi.domain.Course;

import java.util.List;
import java.util.Map;

@PersistenceAdapter
@RequiredArgsConstructor
public class MomentPersistentAdapter implements MomentRepositoryPort {
    private final MomentRepository momentRepository;

    @Override
    public Long countByCourse(Course course) {
        return momentRepository.countByCourse(course);
    }

    @Override
    public Map<Long, Long> countByCourses(List<Course> courses) {
        List<MomentRepository.MomentCount> momentCounts = momentRepository.countByCourses(courses);

        return momentCounts.stream()
                .collect(
                        Map::of,
                        (map, momentCount) -> map.put(momentCount.getCourseId(), momentCount.getCount()),
                        Map::putAll);
    }
}
