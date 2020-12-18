package com.shuttle.apply.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.apply.dto.ApplyDTO;
import com.shuttle.apply.service.ApplyService;
import com.shuttle.user.util.WithAccount;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.shuttle.apply.dto.ApplyDTO.stirngToLocalDateTimeConverter;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
class ApplyApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplyApiController applyApiController;

    @Autowired
    ApplyService applyService;

    ApplyDTO makeDTO() {
        return ApplyDTO.builder()
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
    }

    @Test
    @WithAccount("username")
    @DisplayName("등록작업 테스트")
    void runApplyRegister() throws Exception {
        mockMvc.perform(post("/apply/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(makeDTO())))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("등록작업 메서드 출력")
    void runApplyRegisterAction(){
        log.info(applyApiController.registerApplyAction(makeDTO()).toString());
    }

    @Test
    @DisplayName("삭제작업 메서드 출력")
    void runApplyRemoveAction(){
        log.info(applyService.register(makeDTO()).toString());
        long applyIdRegistered=applyService.register(makeDTO()).getApplyId();
        log.info("Registered Id: "+applyIdRegistered);
        log.info(applyApiController.removeApplyAction(applyIdRegistered).toString());
    }

    @Test
    @WithAccount("username")
    @DisplayName("삭제작업 테스트")
    void runApplyRemove() throws Exception {

        long applyId=applyService.register(makeDTO()).getApplyId();
        log.info("applyId: "+applyId);

        mockMvc.perform(delete("/apply/delete")
                .with(csrf())
                .param("applyId",applyId+""))
                .andExpect(status().isOk())
                .andExpect(content().string("removeApplyAction success"));
    }


}