package com.shuttle.admin.repository;

import com.shuttle.admin.domain.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminRepository extends JpaRepository<Admin, Long> {

   // public Admin findbyName(String name);
}
