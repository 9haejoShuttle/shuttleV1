package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.repository.ApplyRepository;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository applyRepository;

    @Override
    public Apply register(ApplyDTO applyDTO) {
        log.info(applyDTO.dataToDomain(applyDTO).toString());
        return applyRepository.save(applyDTO.dataToDomain(applyDTO));
    }

    @Override
    public boolean remove(long applyId) {
        try {
            Apply apply = applyRepository.findByApplyId(applyId);
            applyRepository.delete(apply);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }}

    @Override
    public Page<Apply> getApplyPageList(Pageable pageable) {
        return applyRepository.getApplyPageListByApplyId(pageable);
    }
}
