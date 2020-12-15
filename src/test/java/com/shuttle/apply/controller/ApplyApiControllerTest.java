package com.shuttle.apply.controller;

import com.shuttle.apply.service.ApplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class ApplyApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplyService applyService;

    @Test
    private void applyRegisterPostTest(){

    }

}