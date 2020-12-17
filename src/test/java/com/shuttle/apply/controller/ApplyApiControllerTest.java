package com.shuttle.apply.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.user.util.WithAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;

import static com.shuttle.apply.dto.ApplyDTO.stirngToLocalDateTimeConverter;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplyApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplyApiController applyApiController;

    @Test
    @WithAccount("username")
    @DisplayName("등록작업 테스트")
    void runApplyRegister() throws Exception {
        ApplyDTO applyDTO = ApplyDTO.builder()
                .userId(1L)
                .startAddr("서울시 성북구")
                .startLat(127.32415532d)
                .startLng(37.123145362d)
                .arrivalAddr("서울시 중구")
                .arrivalLat(127.2554252525d)
                .arrivalLng(34.1242145d)
                .arrivalTime(710L)
                .memo("")
                .regdate("2020-12-15T07:25:46.957Z")
                .build();

        mockMvc.perform(post("/apply/regiser")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(applyDTO)))
                .andExpect(status().isOk());

    }
}