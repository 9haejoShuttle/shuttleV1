package com.shuttle.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLY_ID")
    private long applyId;

    private long userId;

    private String startAddr;
    private long startLng;
    private long startLat;

    private String arrivalAddr;
    private long arrivalLng;
    private long arrivalLat;

    private Time arrivalTime;
    private String memo;
    private LocalDateTime regdate;


}

