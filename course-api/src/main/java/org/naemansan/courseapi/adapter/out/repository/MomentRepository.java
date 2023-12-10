package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Moment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
    Long countByCourse(Course course);

    @Query("select m.course.id as courseId, count(m.id) as count " +
            "from Moment m join m.course c where c in :courses group by m.course.id")
    List<MomentCount> countByCourses(List<Course> courses);

    @EntityGraph(attributePaths = {"course"})
    Page<Moment> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"course"})
    Page<Moment> findByCourse(Course course, Pageable pageable);

    interface MomentCount {
        Long getCourseId();
        Long getCount();
    }
}
