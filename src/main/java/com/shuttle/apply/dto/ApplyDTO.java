/*
 * Author:Recflow
 */
package com.shuttle.apply.dto;

import com.shuttle.domain.Apply;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@ToString
public class ApplyDTO {
    /* DB상의 Apply 테이블에 들어간 칼럼들
    apply_id, user_id,
    start_addr, start_lng, start_lat
    arrival_addr, arrival_lng, arrival_lat
    arrival_time
    memo, regdate
    */
    private final long userId;
    private final String startAddr, arrivalAddr;
    private final double startLng, startLat, arrivalLng, arrivalLat;
    private final long arrivalTime;
    private final String memo;
    private final String regdate;


    public static LocalDateTime stirngToLocalDateTimeConverter(String source) {
        return LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
    }
    /*regdate 받아온 값을 localdatetime 으로 변경하는 메소드 만들기*/
    public Apply dataToDomain(ApplyDTO applyDTO){
        return Apply.builder()
                .userId(applyDTO.userId)
                .startAddr(applyDTO.startAddr)
                .startLat(applyDTO.startLat)
                .startLng(applyDTO.startLng)
                .arrivalAddr(applyDTO.arrivalAddr)
                .arrivalLat(applyDTO.arrivalLat)
                .arrivalLng(applyDTO.arrivalLng)
                .arrivalTime(Time.valueOf((int)applyDTO.arrivalTime/100+":"+applyDTO.arrivalTime%100+":00"))
                .memo(""+applyDTO.memo)
                .regdate(stirngToLocalDateTimeConverter(applyDTO.regdate))
                .build();
    }

}
