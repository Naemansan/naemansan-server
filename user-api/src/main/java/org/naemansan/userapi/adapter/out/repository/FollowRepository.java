package org.naemansan.userapi.adapter.out.repository;

import org.naemansan.userapi.domain.Follow;
import org.naemansan.userapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingAndFollower(User following, User followed);

    @EntityGraph(attributePaths = {"follower"})
    Page<Follow> findByFollowing(User following, Pageable pageable);

    @EntityGraph(attributePaths = {"following"})
    Page<Follow> findByFollower(User followed, Pageable pageable);

    Long countByFollowing(User user);

    Long countByFollower(User user);
}
