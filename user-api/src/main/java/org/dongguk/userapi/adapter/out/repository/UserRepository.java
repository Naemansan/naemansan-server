package org.dongguk.userapi.adapter.out.repository;

import org.dongguk.userapi.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"tags"})
    Optional<User> findUserDetailByUuid(UUID uuid);

    @Query("select u.uuid as uuid, u.nickname as nickname, u.profileImageUrl as profileImageUrl " +
            "from User u where u.uuid = :uuid")
    Optional<UserName> findUserNameByUuid(UUID uuid);

    @Query("select u.uuid as uuid, u.nickname as nickname, u.profileImageUrl as profileImageUrl " +
            "from User u where u.uuid in :uuids")
    List<UserName> findUserNamesByUuids(List<UUID> uuids);

    interface UserName {
        UUID getUuid();
        String getNickname();
        String getProfileImageUrl();
    }
}
