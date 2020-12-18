package com.shuttle.apply.controller;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apply/*")
@Log4j2
public class ApplyApiController {

    private final ApplyService applyService;

    //신청내용 등록 Post
    @PostMapping("/register")
    public ResponseEntity<String> registerApplyAction(@RequestBody ApplyDTO applyDTO) {
        //start_addr/start_lng/start_lat
        //arrival_addr/arrival_lng/arrival_lat
        //arrival_time/memo
        log.info("register POST.................");
        log.info(applyDTO.toString());

        if (applyService.register(applyDTO) != null) {
            log.info("ApplyRegister : true");
            return new ResponseEntity<>("registerApplyAction success, appliedId: "+applyService.register(applyDTO).getApplyId(), HttpStatus.OK);
        }
        log.info("ApplyRegister : false");
        return new ResponseEntity<>("registerApplyAction fail", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeApplyAction(@RequestParam("applyId") long applyId){
        if (applyService.remove(applyId))
            return new ResponseEntity<>("removeApplyAction success", HttpStatus.OK);

        return new ResponseEntity<>("removeApplyAction fail", HttpStatus.NOT_FOUND);

    }
}
