package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
    Long countByCourse(Course course);


    @Query("select m.course.id as courseId, count(m.id) as count " +
            "from Moment m where m.course in :courses group by m.course.id")
    List<MomentCount> countByCourses(List<Course> courses);

    interface MomentCount {
        Long getCourseId();
        Long getCount();
    }
}
