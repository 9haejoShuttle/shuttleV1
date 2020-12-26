package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import com.shuttle.user.UserRepository;
import com.shuttle.user.util.WithAccount;
import lombok.With;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @AfterEach
    void deleteAll () {
        paymentRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Transactional
    @WithAccount("01012341234")
    @Test
    void testFindByPaymentWithUser() {
        User user = userRepository.findByPhone("01012341234").orElseThrow();

        //Payment에 100개의 더미 데이터 넣기
        for (int i=0; i<100; i++ ) {
            //저장할 Payment Entity
            Payment paymentEntity = PaymentApiControllerTest
                    .getPaymentCompleteResultDto()
                    .toEntity();

            //Payment에 User정보 추가
            paymentEntity.addUser(user);

            //DB저장
            Payment save = paymentRepository.save(paymentEntity);
        }

        Page<Payment> paymentWithUser =
                paymentRepository.findByPaymentWithUser(user, PageRequest.of(0, 10, Sort.by("id").descending()));

        assertTrue(paymentWithUser.hasContent());
        assertEquals(paymentWithUser.getTotalElements(), 100);
        assertEquals(paymentWithUser.getTotalPages(), 10);
        assertEquals(paymentWithUser.getNumberOfElements(), 10);
        assertEquals(paymentWithUser.getNumber(), 0);

    }

    @Test
    @Transactional
    @WithAccount("01012341234")
    void test_findByUserId() {
        User user = userRepository.findByPhone("01012341234").orElseThrow();

        //Payment에 100개의 더미 데이터 넣기
        for (int i=0; i<100; i++ ) {
            //저장할 Payment Entity
            Payment paymentEntity = PaymentApiControllerTest
                    .getPaymentCompleteResultDto()
                    .toEntity();

            //Payment에 User정보 추가
            paymentEntity.addUser(user);

            //DB저장
            Payment save = paymentRepository.save(paymentEntity);
        }

        System.out.println("--------END FOR LOOP---------");

        Page<Payment> findByUser = paymentRepository.findByUser(user, PageRequest.of(0, 10, Sort.by("id").descending()));

        System.out.println("------------------------------");

        assertTrue(findByUser.hasContent());
        assertEquals(findByUser.getTotalElements(), 100);
        assertEquals(findByUser.getTotalPages(), 10);
        assertEquals(findByUser.getNumberOfElements(), 10);
        assertEquals(findByUser.getNumber(), 0);


    }
}