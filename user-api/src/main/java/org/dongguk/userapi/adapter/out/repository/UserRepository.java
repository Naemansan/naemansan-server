package org.dongguk.userapi.adapter.out.repository;

import org.dongguk.userapi.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"tags"})
    Optional<User> findUserDetailByUuid(UUID uuid);
}
