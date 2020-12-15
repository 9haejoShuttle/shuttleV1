package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.repository.ApplyRepository;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ApplyServiceImpl implements ApplyService{

    ApplyRepository applyRepository;

    @Override
    public Apply register(ApplyDTO applyDTO) {
       return applyRepository.save(applyDTO.dataToDomain(applyDTO));
    }
}
