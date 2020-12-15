package com.shuttle.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLY_ID")
    private long applyId;

    private long userId;

    private String startAddr;
    private double startLng, startLat;

    private String arrivalAddr;
    private double arrivalLng, arrivalLat;

    private Time arrivalTime;
    private String memo;
    private LocalDateTime regdate;


}

