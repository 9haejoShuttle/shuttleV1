package com.shuttle.apply.repository;


import com.shuttle.domain.Apply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

import static com.shuttle.apply.dto.ApplyDTO.stirngToLocalDateTimeConverter;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Log4j2
class ApplyRepositoryTest {

    @Autowired
    ApplyRepository applyRepository;

    Apply makeEntity(int i){
        return Apply.builder()
                .userId(1L)
                .startAddr("서울시 성북구"+i)
                .startLat(127.32415532d)
                .startLng(37.123145362d)
                .arrivalAddr("서울시 중구"+i)
                .arrivalLat(127.2554252525d)
                .arrivalLng(34.1242145d)
                .arrivalTime(new Time(710L))
                .memo(""+i)
                .regdate(stirngToLocalDateTimeConverter("2020-12-15T07:25:46.957Z"))
                .build();
    }
    @Test
    void createTestInRepository() {
        for(int i=0;i<100;i++) {
            log.info(makeEntity(i).toString());
            applyRepository.save(makeEntity(i));
        }
    }
    @Test
    void deleteTestInRepository(){
        Apply apply=applyRepository.save(makeEntity(0));
        long applyIdForTest=apply.getApplyId();
        log.info(applyIdForTest+", "+apply.toString());
        applyRepository.delete(apply);
        if(applyRepository.findByApplyId(applyIdForTest)!=null) {
            log.info("삭제 실패"+ applyRepository.findByApplyId(applyIdForTest).toString());
        }else{
            log.info("삭제 성공");
        }
    }
}