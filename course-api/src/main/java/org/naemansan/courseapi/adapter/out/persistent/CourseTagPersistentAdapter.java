package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.courseapi.adapter.out.repository.CourseTagRepository;
import org.naemansan.courseapi.application.port.out.CourseTagRepositoryPort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.CourseTag;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class CourseTagPersistentAdapter implements CourseTagRepositoryPort {
    private final CourseTagRepository courseTagRepository;

    @Override
    public List<CourseTag> createCourseTags(List<Long> tagIds, Course course) {
        return courseTagRepository.saveAll(tagIds.stream()
                .map(tagId -> new CourseTag(tagId, course))
                .toList());
    }

    @Override
    public void deleteCourseTags(List<CourseTag> courseTags) {
        courseTagRepository.deleteAll(courseTags);
        courseTagRepository.flush();
    }
}
