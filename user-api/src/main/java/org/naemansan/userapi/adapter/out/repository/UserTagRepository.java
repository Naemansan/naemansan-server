package org.naemansan.userapi.adapter.out.repository;

import org.naemansan.userapi.domain.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
}
