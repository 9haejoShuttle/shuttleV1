package com.shuttle.domain;

import com.sun.istack.Nullable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLY_ID")
    private long applyId;

    // user Table과는 연결하지 않기로 함.
    // 현재 운행노선일 경우, 탈퇴 시 해당 신청 노선이 삭제 될 위험이 있음.
    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String startAddr;

    @Column(nullable = false)
    private double startLng, startLat;

    @Column(nullable = false)
    private String arrivalAddr;
    @Column(nullable = false)
    private double arrivalLng, arrivalLat;

    @Column(nullable = false)
    private Time arrivalTime;

    @Column(nullable = false)
    private String memo;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime regdate;

}

