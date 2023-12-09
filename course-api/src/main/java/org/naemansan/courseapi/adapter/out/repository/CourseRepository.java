package org.naemansan.courseapi.adapter.out.repository;

import jakarta.persistence.Entity;
import org.naemansan.courseapi.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = {"tags"})
    Optional<Course> findById(Long id);

    Optional<Course> findByIdAndUserId(Long id, UUID userId);
}
