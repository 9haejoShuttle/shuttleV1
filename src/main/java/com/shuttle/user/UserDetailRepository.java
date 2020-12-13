package com.shuttle.user;

import com.shuttle.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
