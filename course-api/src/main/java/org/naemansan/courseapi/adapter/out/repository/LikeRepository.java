package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByUserIdAndCourse(UUID userId, Course course);

    @Query("select l.course.id as courseId, case when count(l.id) > 0 then true else false end as exists " +
            "from Like l where l.course in :courses and l.userId = :userId group by l.course.id")
    List<LikeExists> existsByUserIdAndCourses(@Param("userId") UUID userId, @Param("courses") List<Course> courses);

    Long countByCourse(Course course);

    @Query("select l.course.id as courseId, count(l.id) as count " +
            "from Like l where l.course in :courses group by l.course.id")
    List<LikeCount> countByCourses(List<Course> courses);

    Optional<Like> findByUserIdAndCourse(UUID uuid, Course course);


    interface LikeCount {
        Long getCourseId();
        Long getCount();
    }

    interface LikeExists {
        Long getCourseId();
        Boolean getExists();
    }
}
