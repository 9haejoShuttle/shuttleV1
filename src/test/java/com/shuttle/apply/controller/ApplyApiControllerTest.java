package com.shuttle.apply.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.IntStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    ApplyDTO makeDTO(int i) {
        return ApplyDTO.builder()
                .userId(i)
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
    @WithAccount("username")
    @DisplayName("등록작업 테스트")
    void runApplyRegister() throws Exception {
        mockMvc.perform(post("/apply/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(makeDTO(0))))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("등록작업 메서드 출력")
    void runApplyRegisterAction() {
        log.info(applyApiController.registerApplyAction(makeDTO(0)).toString());
    }

    @Test
    @DisplayName("삭제작업 메서드 출력")
    void runApplyRemoveAction() {
        log.info(applyService.register(makeDTO(0)).toString());
        long applyIdRegistered = applyService.register(makeDTO(0)).getApplyId();
        log.info("Registered Id: " + applyIdRegistered);
        log.info(applyApiController.removeApplyAction(applyIdRegistered).toString());
    }

    @Test
    @WithAccount("username")
    @DisplayName("삭제작업 테스트")
    void runApplyRemove() throws Exception {

        long applyId = applyService.register(makeDTO(0)).getApplyId();
        log.info("applyId: " + applyId);

        mockMvc.perform(delete("/apply/delete")
                .with(csrf())
                .param("applyId", applyId + ""))
                .andExpect(status().isOk())
                .andExpect(content().string("removeApplyAction success"));
    }

    @Test
    @WithAccount("username")
    @DisplayName("목록 페이징 테스트")
    void runGetApplyListWithPaging() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(i -> applyApiController.registerApplyAction(makeDTO(i)));
        mockMvc.perform(MockMvcRequestBuilders.get("/apply/list/{page}", 1)
                .with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


}