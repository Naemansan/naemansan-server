package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {
}
