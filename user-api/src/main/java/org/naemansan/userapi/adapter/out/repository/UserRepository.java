package org.naemansan.userapi.adapter.out.repository;

import org.naemansan.userapi.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @EntityGraph(attributePaths = {"tags"})
    Optional<User> findById(UUID uuid);

    @Query("select u.id as id, u.nickname as nickname, u.profileImageUrl as profileImageUrl " +
            "from User u where u.id = :id")
    Optional<UserName> findUserNameById(@Param("id") UUID id);

    @Query("select u.id as id, u.nickname as nickname, u.profileImageUrl as profileImageUrl " +
            "from User u where u.id in :ids")
    List<UserName> findUserNamesByIds(@Param("ids") List<UUID> ids);

    interface UserName {
        UUID getId();
        String getNickname();
        String getProfileImageUrl();
    }
}
