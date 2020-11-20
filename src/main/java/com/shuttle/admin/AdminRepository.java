package com.shuttle.admin;

import com.shuttle.domain.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminRepository extends JpaRepository<Admin, Long> {
    //동일한 아이디가 존재하는지 체크할 때 사용
    boolean existsByName(String name);

    // public Admin findbyName(String name);
}
