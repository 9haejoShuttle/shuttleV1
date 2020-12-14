package com.shuttle.payment;


import lombok.*;

@Getter @Setter @ToString
public class PaymentCompleteResultDto {
    private String applyNum;
    private String buyerEmail;
    private int cardQuaota;
    private String impUid; //아임포트 결제 고유 ID
    private String merchantUid; //아임포트에서 주는 클라이언트 고유 ID
    private String name;
    private Long paidAmount;
    private String paidAt; // UNIX TIME
    private String payMethod;
    private String provider;
    private String pgTid;
    private String pgType;
    private boolean success;
}
