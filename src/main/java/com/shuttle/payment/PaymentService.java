package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public void save(User user, PaymentCompleteResultDto paymentResult) {
        paymentRepository.save(paymentResult.toEntity(user));
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
