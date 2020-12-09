package com.shuttle.apply.repository;

import com.shuttle.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, String> {

    @Transactional
    @Query("select ap.applyId from Apply ap")
    List<Apply> findAll();

    @Transactional
    @Query("select ap.applyId from Apply ap where ap.applyId = :applyId")
    Apply findAppliesByApplyId(@Param("applyId") String applyId);
}
