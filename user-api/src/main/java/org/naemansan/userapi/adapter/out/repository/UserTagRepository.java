package org.naemansan.userapi.adapter.out.repository;

import org.naemansan.userapi.domain.User;
import org.naemansan.userapi.domain.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {

    @Modifying
    @Query("delete from UserTag ut where ut.tagId in :tagIds and ut.user = :user")
    void deleteByTagIdsAndUser(List<Long> tagIds, User user);
}
