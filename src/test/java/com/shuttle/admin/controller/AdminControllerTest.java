package com.shuttle.admin.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@SpringBootTest
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void test_saveAdmin() throws Exception {
		mockMvc.perform(post("/admin/new")
				.param("name", "@!#!@#!@")
				.param("password", "1234")
				.with(csrf()))
		.andExpect(status().is4xxClientError());
	}
}
