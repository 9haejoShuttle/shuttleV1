package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.domain.Apply;

public interface ApplyService {
    Apply register(ApplyDTO applyDTO);

    boolean remove(long applyId);
}

