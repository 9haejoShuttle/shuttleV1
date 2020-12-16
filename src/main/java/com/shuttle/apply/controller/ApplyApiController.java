package com.shuttle.apply.controller;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> register(@RequestBody ApplyDTO applyDTO) {
        //start_addr/start_lng/start_lat
        //arrival_addr/arrival_lng/arrival_lat
        //arrival_time/memo
        log.info("register POST.................");
        log.info(applyDTO.toString());

        if (applyService.register(applyDTO)!=null) {
            log.info("ApplyRegister : true");
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        log.info("ApplyRegister : false");
        return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
    }
}
