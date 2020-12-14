package com.shuttle.payment;

import com.shuttle.domain.User;
import com.shuttle.user.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @PostMapping("/payments/complete")
    public void paymentComplete(@CurrentUser User user, @RequestBody PaymentCompleteResultDto response) {

    }
}
