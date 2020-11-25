package com.shuttle.user;

import com.shuttle.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    Optional<User> findByPhone(String phone);
}
