package com.shuttle.admin.repository;

import com.shuttle.admin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
}
