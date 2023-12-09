package org.naemansan.userapi.adapter.out.repository;

import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingAndFollowed(User following, User followed);

    @EntityGraph(attributePaths = {"followed"})
    List<Follow> findByFollowing(User following);

    @EntityGraph(attributePaths = {"following"})
    List<Follow> findByFollowed(User followed);
}
