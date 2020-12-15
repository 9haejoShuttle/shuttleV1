package com.shuttle.payment;


import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Getter @Setter @ToString @Builder
public class PaymentCompleteResultDto {
    private User user;
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

    public Payment toEntity(User user) {

//        Timestamp unixTime = Timestamp.valueOf(paidAt);

        long epochtime = Long.parseLong(paidAt);

        LocalDateTime unixTimeToLocalDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(epochtime),
                        TimeZone.getDefault().toZoneId());


        return Payment.builder()
                .user(user)
                .applyNum(applyNum)
                .buyerEmail(buyerEmail)
                .cardQuaota(cardQuaota)
                .impUid(impUid)
                .merchantUid(merchantUid)
                .name(name)
                .paidAmount(paidAmount)
                .payMethod(payMethod)
                .paidAt(unixTimeToLocalDateTime)
                .provider(provider)
                .pgTid(pgTid)
                .pgType(pgType)
                .success(success)
                .build();
    }
}
