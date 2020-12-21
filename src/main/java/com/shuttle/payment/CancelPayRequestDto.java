package com.shuttle.payment;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class CancelPayRequestDto {
    Long id;
    String reason;
}
