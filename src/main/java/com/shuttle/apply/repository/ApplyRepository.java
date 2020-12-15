package com.shuttle.apply.repository;


import com.shuttle.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, String> {

//    @Transactional
//    @Query("select ap.applyId from Apply ap")
//    List<Apply> findAll();
//
//    @Transactional
//    @Query("select ap.applyId from Apply ap where ap.applyId = :applyId")
//    Apply findAppliesByApplyId(@Param("applyId") String applyId);
}
