package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.courseapi.adapter.out.repository.LikeRepository;
import org.naemansan.courseapi.application.port.out.LikeRepositoryPort;
import org.naemansan.courseapi.domain.Course;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class LikePersistentAdapter implements LikeRepositoryPort {
    private final LikeRepository likeRepository;

    @Override
    public Boolean existsByCourseAndUserId(Course course, UUID userId) {
        return likeRepository.existsByCourseAndUserId(course, userId);
    }

    @Override
    public Map<Long, Boolean> existsByCoursesAndUserId(List<Course> course, UUID userId) {
        List<LikeRepository.LikeExists> likeExists = likeRepository.existsByCoursesAndUserId(course, userId);

        Map<Long, Boolean> result = new java.util.HashMap<>();

        likeExists.forEach(likeExist -> result.put(likeExist.getCourseId(), likeExist.getExists()));

        return result;
    }

    @Override
    public Long countByCourse(Course course) {
        return likeRepository.countByCourse(course);
    }

    @Override
    public Map<Long, Long> countByCourses(List<Course> courses) {
        List<LikeRepository.LikeCount> likeCounts = likeRepository.countByCourses(courses);

        Map<Long, Long> result = new java.util.HashMap<>();

        likeCounts.forEach(likeCount -> result.put(likeCount.getCourseId(), likeCount.getCount()));

        return result;
    }
}
