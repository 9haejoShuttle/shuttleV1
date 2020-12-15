package com.shuttle.apply.repository;


import com.shuttle.domain.Apply;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;

import static com.shuttle.apply.dto.ApplyDTO.stirngToLocalDateTimeConverter;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ApplyRepositoryTest {

    ApplyRepository applyRepository;

    @Test
    public void createTest() {
        Apply apply = Apply.builder()
                .userId(1L)
                .startAddr("서울시 성북구")
                .startLat(127.32415532)
                .startLng(37.123145362)
                .arrivalAddr("서울시 중구")
                .arrivalLat(127.2554252525)
                .arrivalLng(34.1242145)
                .arrivalTime(new Time(71000))
                .regdate(stirngToLocalDateTimeConverter("2020-12-15T07:25:46.957Z"))
                .build();
        applyRepository.save(apply);
    }
}