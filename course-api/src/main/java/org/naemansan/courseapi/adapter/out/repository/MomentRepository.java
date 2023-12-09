package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
}
