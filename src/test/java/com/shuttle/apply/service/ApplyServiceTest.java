package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.repository.ApplyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class ApplyServiceTest {

    @Autowired
    ApplyRepository applyRepository;


    ApplyDTO serveDTO(long i) {
        return ApplyDTO.builder()
                .userId(1L + i % 10)
                .startAddr("서울시 성북구" + i)
                .startLat(127.32415532d)
                .startLng(37.123145362d)
                .arrivalAddr("서울시 중구" + i)
                .arrivalLat(127.2554252525d)
                .arrivalLng(34.1242145d)
                .arrivalTime(710L)
                .memo("")
                .regdate("2020-12-15T07:25:46.957Z")
                .build();
    }

    @Test
    void register() {
        for (long i = 0; i < 100; i++) {
            ApplyDTO applyDTO = serveDTO(i);
            log.info(applyDTO.toString());
            log.info(applyDTO.dataToDomain(applyDTO).toString());
            applyRepository.save(applyDTO.dataToDomain(applyDTO));
        }
    }

    @Test
    void select() {
        for (long i = 0; i < 100; i++) {
            ApplyDTO applyDTO = serveDTO(i);
            log.info(applyDTO.toString());
            log.info(applyDTO.dataToDomain(applyDTO).toString());
            applyRepository.save(applyDTO.dataToDomain(applyDTO));
        }
        applyRepository.findByUserId(1L);
    }


}