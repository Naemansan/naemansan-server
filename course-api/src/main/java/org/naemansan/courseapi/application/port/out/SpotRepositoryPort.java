package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Spot;
import org.naemansan.courseapi.dto.persistent.SpotPersistent;

import java.util.List;

public interface SpotRepositoryPort {

    void save(SpotPersistent spot, Course course);

    void saveAll(List<SpotPersistent> spots, Course course);

    void delete(Spot spot);

    void deleteAll(List<Spot> spots);

    List<Spot> findByCourse(Course course);
}
