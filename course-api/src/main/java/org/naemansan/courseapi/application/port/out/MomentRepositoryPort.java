package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Moment;
import org.naemansan.courseapi.dto.response.MomentListDto;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.dto.type.EWeather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MomentRepositoryPort {

    Moment createMoment(String userId,
                        Course course,
                        String content,
                        EEmotion emotion,
                        EWeather weather);

    Page<Moment> findMoments(Pageable pageable);

    Page<Moment> findMomentsByCourse(Course course, Pageable pageable);

    Long countByCourse(Course course);

    Map<Long, Long> countByCourses(List<Course> courses);


}
