package com.shuttle.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.Payment;
import com.shuttle.user.util.WithAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PaymentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired PaymentRepository paymentRepository;

    @AfterEach
    void deleteAll() {
        paymentRepository.deleteAll();
    }

    @DisplayName("결제 성공 데이터 추가")
    @WithAccount("12341234")
    @Test
    void test_payment_success () throws Exception {
        PaymentCompleteResultDto paymentCompleteResultDto
                = getPaymentCompleteResultDto();

        mockMvc.perform(post("/payments/complete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(paymentCompleteResultDto)))
                .andExpect(status().isOk());

        Payment payment = paymentRepository.findAll().get(0);

        assertEquals(payment.getApplyNum(), paymentCompleteResultDto.getApplyNum());
        assertEquals(payment.getName(), paymentCompleteResultDto.getName());
        assertEquals(payment.getBuyerEmail(), paymentCompleteResultDto.getBuyerEmail());
        assertEquals(payment.getPayMethod(), paymentCompleteResultDto.getPayMethod());
        assertEquals(payment.getCardQuaota(), paymentCompleteResultDto.getCardQuaota());
        assertEquals(payment.getMerchantUid(), paymentCompleteResultDto.getMerchantUid());
        assertEquals(payment.getImpUid(), paymentCompleteResultDto.getImpUid());
        assertEquals(payment.isSuccess(), paymentCompleteResultDto.isSuccess());
    }

    private PaymentCompleteResultDto getPaymentCompleteResultDto() {
        return PaymentCompleteResultDto.builder()
                .applyNum(Math.random()*1000 + "")
                .buyerEmail("air@pot.com")
                .cardQuaota(0)
                .impUid(UUID.randomUUID().toString())
                .merchantUid(UUID.randomUUID().toString())
                .paidAmount(150000L)
                .name("모두의 셔틀 탑승권")
                .paidAt("1608009160")
                .payMethod("payco")
                .pgTid(UUID.randomUUID().toString())
                .provider("결제 제공 업체 OO")
                .pgType("PG사 TYPE")
                .success(true)
                .build();
    }

}