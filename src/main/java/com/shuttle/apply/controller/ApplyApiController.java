package com.shuttle.apply.controller;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apply/*")
@Log4j2
public class ApplyApiController {

    private final ApplyService applyService;

    @PostMapping("/register")
    public void register(@RequestBody ApplyDTO applyDTO) {
        //start_addr/start_lng/start_lat
        //arrival_addr/arrival_lng/arrival_lat
        //arrival_time/memo
        log.info("register POST.................");
        log.info(applyDTO.toString());

        long applyId = applyService.register(applyDTO).getApplyId();
        log.info(applyId);
        //return "/read/"/*+해당 노선을 등록한 글 번호로 이동 */;
        // return ResponseEntity.ok(""+applyId);
    }
}
