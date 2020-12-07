package com.shuttle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    Long id;

    @ManyToOne
    User user;

    @Column(name = "PAYMENT_METHOD", nullable = false)
    String paymentMethod;

    @Column(nullable = false)
    Long amount;

    @Column(name = "PAYMENT_DATE", nullable = false)
    @CreatedDate
    LocalDateTime paymentDate;

}
