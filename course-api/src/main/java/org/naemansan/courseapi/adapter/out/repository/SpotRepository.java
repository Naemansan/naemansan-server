package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByCourse(Course course);
}
