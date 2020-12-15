package com.shuttle.apply.service;

import com.shuttle.apply.repository.ApplyRepository;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ApplyServiceImpl implements ApplyService{

    ApplyRepository applyRepository;


    @Override
    public List<Apply> getAppliedList() {
        return applyRepository.findAll();
    }

    @Override
    public Optional<Apply> getApplicationByApplyId(long applyId) {
        return applyRepository.findById(""+applyId);
    }
}
