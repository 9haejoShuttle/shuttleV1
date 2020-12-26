package com.shuttle.payment;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "SELECT p" +
            " FROM Payment p INNER JOIN User u ON p.user = u" +
            " WHERE p.user = :user")
    Page<Payment> findByPaymentWithUser(User user, Pageable pageable);

    Page<Payment> findByUser(User user, Pageable pageable);
}
