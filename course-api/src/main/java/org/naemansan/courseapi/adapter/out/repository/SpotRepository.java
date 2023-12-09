package org.naemansan.courseapi.adapter.out.repository;

import org.naemansan.courseapi.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
}
