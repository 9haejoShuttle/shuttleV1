package com.shuttle.apply.service;

import com.shuttle.domain.Apply;

import java.util.List;

public interface ApplyService {

    List<Apply> getAppliedList();
    Apply getApplicationByApplyId(long applyId);

}
