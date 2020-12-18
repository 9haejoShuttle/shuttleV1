package com.shuttle.apply.repository;


import com.shuttle.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ApplyRepository extends JpaRepository<Apply, String> {
    List<Apply> findByUserId(long userId);
    void delete(Apply apply);
    Apply findByApplyId(long applyId);
}
