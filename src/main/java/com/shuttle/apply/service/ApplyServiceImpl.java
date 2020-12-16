package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.repository.ApplyRepository;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class ApplyServiceImpl implements ApplyService {

    ApplyRepository applyRepository;

    @Override
    public Apply register(ApplyDTO applyDTO) {
        log.info(applyDTO.dataToDomain(applyDTO).toString());
        return applyRepository.save(applyDTO.dataToDomain(applyDTO));
    }
}
