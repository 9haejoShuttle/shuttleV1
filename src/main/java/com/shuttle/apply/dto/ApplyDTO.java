/*
 * Author:Recflow
 */
package com.shuttle.apply.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDateTime;

@Builder
@Getter
public class ApplyDTO {
    /* DB상의 Apply 테이블에 들어간 칼럼들
    apply_id, user_id,
    start_addr, start_lng, start_lat
    arrival_addr, arrival_lng, arrival_lat
    arrival_time
    memo, regdate
    */

    private final String startAddr, arrivalAddr;
    private final double startLng, startLat, arrivalLng, arrivalLat;
    private final Time arrivalTime;
    private final String memo;
    private final LocalDateTime regdate;

    /*regdate 받아온 값을 localdatetime 으로 변경하는 메소드 만들기*/

}
