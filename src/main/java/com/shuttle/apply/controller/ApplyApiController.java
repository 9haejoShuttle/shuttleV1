package com.shuttle.apply.controller;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.service.ApplyService;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ApplyApiController {

    private final ApplyService applyService;

    @GetMapping("/apply")
    public Page<Apply> readApplyWithPageAction(@RequestParam @Valid final int page) {
        //추후 할 일
        final int SIZE = 10;
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by("regdate").descending());

        return applyService.getApplyPageListByApplyId(pageable);
    }

    //신청 내용 보내는 method Return 값이 없는데...
    @GetMapping("/apply/{applyId}")
    public Apply readApply(@PathVariable("applyId") long applyId) {
        return applyService.selectApply(applyId);
    }

    //신청내용 등록 Post
    @PostMapping("/apply")
    public ResponseEntity<String> registerApplyAction(@RequestBody ApplyDTO applyDTO) {
        //start_addr/start_lng/start_lat
        //arrival_addr/arrival_lng/arrival_lat
        //arrival_time/memo
        log.info("register POST.................");
        log.info(applyDTO.toString());

        if (applyService.register(applyDTO) != null) {
            log.info("ApplyRegister : true");
            return new ResponseEntity<>("registerApplyAction success, appliedId :" + applyService.register(applyDTO).getApplyId(), HttpStatus.OK);
        }
        log.info("ApplyRegister : false");
        return new ResponseEntity<>("registerApplyAction fail", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/apply/{applyId}")
    public ResponseEntity<String> removeApplyAction(@PathVariable("applyId") long applyId) {
        if (applyService.remove(applyId))
            return new ResponseEntity<>("removeApplyAction success", HttpStatus.OK);

        return new ResponseEntity<>("removeApplyAction fail", HttpStatus.NOT_FOUND);

    }
}
