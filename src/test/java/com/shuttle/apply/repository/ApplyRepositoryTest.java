package com.shuttle.apply.repository;


import com.shuttle.domain.Apply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;
import java.util.stream.IntStream;

import static com.shuttle.apply.dto.ApplyDTO.stirngToLocalDateTimeConverter;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Log4j2
class ApplyRepositoryTest {

    @Autowired
    ApplyRepository applyRepository;

    Apply makeEntity(int i) {
        return Apply.builder()
                .userId(1L)
                .startAddr("서울시 성북구" + i)
                .startLat(127.32415532d)
                .startLng(37.123145362d)
                .arrivalAddr("서울시 중구" + i)
                .arrivalLat(127.2554252525d)
                .arrivalLng(34.1242145d)
                .arrivalTime(new Time(710L))
                .memo("" + i)
                .regdate(stirngToLocalDateTimeConverter("2020-12-15T07:25:46.957Z"))
                .build();
    }

    @Test
    void createTestInRepository() {
        IntStream.rangeClosed(1, 100).forEach(i -> applyRepository.save(makeEntity(i)));
    }

    @Test
    void deleteTestInRepository() {
        Apply apply = applyRepository.save(makeEntity(0));
        long applyIdForTest = apply.getApplyId();
        log.info(applyIdForTest + ", " + apply.toString());
        applyRepository.delete(apply);
        if (applyRepository.findByApplyId(applyIdForTest) != null) {
            log.info("삭제 실패" + applyRepository.findByApplyId(applyIdForTest).toString());
        } else {
            log.info("삭제 성공");
        }
    }

    @Test
    void getApplyListWithPagingInRepository() {
        createTestInRepository();
        final int SIZE = 10;
        final int PAGE = 3;
        log.info(
                applyRepository.getApplyPageListByApplyId(
                        PageRequest.of(PAGE, SIZE, Sort.by("regdate").descending())
                ).toList()
        );
    }
}