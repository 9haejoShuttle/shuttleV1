package com.shuttle.apply.service;

import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.repository.ApplyRepository;
import com.shuttle.domain.Apply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
class ApplyServiceTest {

    @Autowired
    ApplyService applyService;

    @Autowired
    ApplyRepository applyRepository;

    ApplyDTO makeDTO(long i) {
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
    void registerTestInService() {
        for (long i = 0; i < 100; i++) {
            ApplyDTO applyDTO = makeDTO(i);
            log.info(applyDTO.toString());
            log.info(applyDTO.dataToDomain(applyDTO).toString());
            applyService.register(applyDTO);
        }
    }

    //테스트 미완성
    @Test
    void selectTestInService() {
        // TODO : 방금 등록한 서비스를 하나만 불러오는 테스트

    }

    @Test
    void getApplyListWithPagingInService(){
        registerTestInService();
        log.info(
                applyService.getApplyPageListByApplyId(
                        PageRequest.of(4,5, Sort.by("regdate").descending())
                ).toList()
        );
    }
    @Test
    void deleteTestInService() {
        registerTestInService();

        if (applyService.remove(12L)) log.info("삭제 성공");
        else log.info("삭제 실패");
    }
}