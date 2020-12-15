package com.shuttle.payment;

import com.shuttle.domain.User;
import com.shuttle.user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments/complete")
    public ResponseEntity paymentComplete(@CurrentUser User user, @RequestBody PaymentCompleteResultDto paymentResult) {
        paymentService.save(user, paymentResult);
        return ResponseEntity.ok().build();
    }
}
