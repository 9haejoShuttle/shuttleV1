package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import com.shuttle.user.UserRepository;
import com.shuttle.user.util.WithAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
*   TODO 사용하지 않을 클래스. 삭제 예정
* */
@Transactional
@SpringBootTest
class PaymentRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;

    @WithAccount("01012341234")
    @Test
    void test_userPaymentHistory() {
        User user = userRepository.findByPhone("01012341234").get();

        for (int i =0; i<10; i++) {
            Payment paymentDtoToEntity = PaymentApiControllerTest
                    .getPaymentCompleteResultDto()
                    .toEntity();

            paymentDtoToEntity.addUser(user);

            Payment savePayment = paymentRepository.save(paymentDtoToEntity);

            List<Payment> paymentsWithUser = paymentRepository.userPaymentHistory(user);

            assertEquals(paymentsWithUser.get(i).getUser(), user);
            assertEquals(paymentsWithUser.get(i).getUser().getPhone(), user.getPhone());
        }
    }
}