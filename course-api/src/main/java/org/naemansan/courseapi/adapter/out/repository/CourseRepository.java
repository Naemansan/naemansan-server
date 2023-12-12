package org.naemansan.courseapi.adapter.out.repository;

import org.locationtech.jts.geom.Point;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.dto.type.EState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @EntityGraph(attributePaths = {"tags"})
    Optional<Course> findById(Long id);

    @EntityGraph(attributePaths = {"tags"})
    Optional<Course> findByIdAndUserId(Long id, UUID userId);

    @EntityGraph(attributePaths = {"tags"})
    List<Course> findByIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"tags"})
    Page<Course> findAllByState(EState state, Pageable pageable);

    @Query(value = "SELECT c.id AS id, c.created_at AS createdAt FROM courses c join course_tags ct on c.id = ct.course_id "
            + "WHERE ct.tag_id in :tagIds AND c.state = 'ENROLLED'",
            countQuery = "SELECT c.id AS id, c.created_at AS createdAt FROM courses c join course_tags ct on c.id = ct.course_id "
                    + "WHERE ct.tag_id in :tagIds AND c.state = 'ENROLLED'",
            nativeQuery = true)
    Page<DateForm> findAllByTagIds(List<Long> tagIds, Pageable pageable);

    @Query(value = "SELECT c.id AS id, ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) AS radius FROM courses c "
            + "WHERE ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) <= 3000 AND c.state = 'ENROLLED'",
            countQuery = "SELECT c.id AS id, ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) AS radius FROM courses c "
                    + "WHERE ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) <= 3000 AND c.state = 'ENROLLED'",
            nativeQuery = true)
    Page<RadiusForm> findAllByLocation(@Param("location") Point location, Pageable pageable);

    @Query(value = "SELECT c.id AS id, ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) AS radius FROM courses c join course_tags ct on c.id = ct.course_id "
            + "WHERE ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) <= 3000 AND ct.tag_id in :tagIds AND c.state = 'ENROLLED'",
            countQuery = "SELECT c.id AS id, ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) AS radius FROM courses c join course_tags ct on c.id = ct.course_id "
                    + "WHERE ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) <= 3000 AND ct.tag_id in :tagIds AND c.state = 'ENROLLED'",
            nativeQuery = true)
    Page<RadiusForm> findAllByTagIdsAndLocation(@Param("tagIds") List<Long> tagIds, @Param("location") Point location, Pageable pageable);

    @Query(value = "SELECT * FROM courses c " +
            "WHERE ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) <= 3000 " +
            "AND c.state = :#{#state.toString()}",
            nativeQuery = true)
    List<Course> findNearCoursesByLocationAndState(
            @Param("location") Point location,
            @Param("state") EState state);

    @Query(value = "SELECT * FROM courses c " +
            "WHERE ST_Distance_Sphere(:location, ST_GeometryN(c.locations, 1)) <= 3000 " +
            "AND c.user_id = :userId AND c.state = :#{#state.toString()}",
            nativeQuery = true)
    List<Course> findNearCoursesByUserIdAndLocationAndState(
            @Param("userId") UUID userId,
            @Param("location") Point location,
            @Param("state") EState state);

    interface RadiusForm {
        Long getId();
        Double getRadius();
    }


    interface DateForm {
        Long getId();
        LocalDate getCreatedAt();
    }
}
