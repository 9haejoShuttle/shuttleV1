package com.shuttle.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String applyNum;

    private String buyerEmail;

    private int cardQuaota;

    private String impUid;  //아임포트 결제 ID

    private String merchantUid; //아임포트에서 주는 클라이언트 고유 ID

    private String name;

    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    private String payMethod;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    private String provider;

    private String pgTid;

    private String pgType;

    private boolean success; //결제 성공 여부

    private boolean cancel; //결제 취소 여부

    public void setCancel() {
        this.cancel = true;
    }
}
