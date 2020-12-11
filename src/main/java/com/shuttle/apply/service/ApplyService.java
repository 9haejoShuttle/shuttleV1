package com.shuttle.apply.service;

import com.shuttle.domain.Apply;

import java.util.List;
import java.util.Optional;

public interface ApplyService {
    List<Apply> getAppliedList();
    Optional<Apply> getApplicationByApplyId(long applyId);
}
