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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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

    @DisplayName("결제 성공 응답 데이터 DB에 저장")
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

    @DisplayName("결제 취소하기")
    @WithAccount("01012341234")
    @Test
    void test_get_access_token() throws Exception {
        //결제 내역 등록
        Payment newPayment = paymentRepository.save(getPaymentCompleteResultDto().toEntity(null));

        //결제 되면 취소 상태가 faslse임
        assertFalse(newPayment.isCancel());

        //취소 요청할 데이터
        CancelPayRequestDto cancelPayRequestDto = new CancelPayRequestDto(1L, "none!!");
        //취소 요청
        mockMvc.perform(post("/payments/cancel")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(cancelPayRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        //정상 취소 되었으면 True로 바뀌었아야 함.
        assertTrue(newPayment.isCancel());
    }

    private PaymentCompleteResultDto getPaymentCompleteResultDto() {
        return PaymentCompleteResultDto.builder()
                .applyNum(Math.random()*1000 + "")
                .buyerEmail("air@pot.com")
                .cardQuaota(0)
                .impUid("imp_642034899587")
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