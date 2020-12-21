package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.domain.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplyService {
    Apply register(ApplyDTO applyDTO);
    boolean remove(long applyId);
    Page<Apply> getApplyPageListByApplyId(Pageable pageable);

    Apply selectApply(long applyId);
}

