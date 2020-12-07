package com.shuttle.apply.service;

import com.shuttle.apply.repository.ApplyRepository;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ApplyServiceImpl implements ApplyService{

    @Autowired
    ApplyRepository applyRepository;

    public List<Apply> getAppliedList(){
        return applyRepository.findAll();
    }

    public Apply getApplicationByApplyId(long applyId){
        return applyRepository.findAppliesByApplyId(""+applyId);
    }

}
