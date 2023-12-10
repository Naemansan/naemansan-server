package org.naemansan.courseapi.adapter.out.persistent;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.PersistenceAdapter;
import org.naemansan.courseapi.adapter.out.repository.SpotRepository;
import org.naemansan.courseapi.application.port.out.SpotRepositoryPort;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.domain.Spot;
import org.naemansan.courseapi.dto.persistent.SpotPersistent;
import org.naemansan.courseapi.dto.request.SpotDto;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class SpotPersistentAdapter implements SpotRepositoryPort {
    private final SpotRepository spotRepository;

    @Override
    public void save(SpotPersistent spot, Course course) {
        spotRepository.save(Spot.builder()
                .title(spot.title())
                .content(spot.content())
                .location(spot.location())
                .thumbnail(spot.thumbnail())
                .category(spot.category())
                .course(course).build());
    }

    @Override
    public void saveAll(List<SpotPersistent> spots, Course course) {
        spotRepository.saveAll(spots.stream()
                .map(spotDto -> Spot.builder()
                        .title(spotDto.title())
                        .content(spotDto.content())
                        .location(spotDto.location())
                        .thumbnail(spotDto.thumbnail())
                        .category(spotDto.category())
                        .course(course).build())
                .toList());
    }

    @Override
    public void delete(Spot spot) {
        spotRepository.delete(spot);
    }

    @Override
    public void deleteAll(List<Spot> spots) {
        spotRepository.deleteAll(spots);
        spotRepository.flush();
    }

    @Override
    public List<Spot> findByCourse(Course course) {
        return spotRepository.findByCourse(course);
    }
}
