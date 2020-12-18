package com.shuttle.apply.repository;


import com.shuttle.domain.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ApplyRepository extends JpaRepository<Apply, String> {
    List<Apply> findByUserId(long userId);

    void delete(Apply apply);

    Apply findByApplyId(long applyId);

    @Query(value = "select a from Apply a",
            countQuery = "select count(a) from Apply a")
    Page<Apply> getApplyPageListByApplyId(Pageable pageable);
}
