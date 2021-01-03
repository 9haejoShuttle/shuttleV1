package com.shuttle.user;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import com.shuttle.payment.PaymentApiControllerTest;
import com.shuttle.payment.PaymentRepository;
import com.shuttle.user.util.WithAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private PaymentRepository paymentRepository;

    @DisplayName("findByUserPaymentHistory메서드 테스트")
    @WithAccount("01012341234")
    //@Test
    void test_findByPayments() {
        //유저 정보 가져오기
        User user = userRepository.findByPhone("01012341234").get();

        for (int i=0; i<10; i++ ) {
            //저장할 Payment Entity
            Payment paymentEntity = PaymentApiControllerTest
                    .getPaymentCompleteResultDto()
                    .toEntity();

            //Payment에 User정보 추가
            paymentEntity.addUser(user);

            //DB저장
            Payment save = paymentRepository.save(paymentEntity);
        }

        //User에서 Payment를 조인해서 데이터를 전부 가져온다.
        User userPaymentHistory = userRepository.findByUserPaymentHistory(user.getId());

        assertEquals(userPaymentHistory.getPayments().size(), 10);

        Long id = 1L;

        for (Payment payment : userPaymentHistory.getPayments()) {
            assertEquals(payment.getId(), id++);
        }
    }
}