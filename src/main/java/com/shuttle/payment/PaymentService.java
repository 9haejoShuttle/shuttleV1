package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import com.shuttle.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public void save(User user, PaymentCompleteResultDto paymentResult) {
        Payment newPayment = paymentResult.toEntity();
        newPayment.addUser(user);

        paymentRepository.save(newPayment);
    }

    public void cancelPayment(Payment payment) {
        payment.setCancel();
    }
}
