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

    @Column(nullable = false)
    private long userId;

    private String startAddr;
    private double startLng, startLat;

    private String arrivalAddr;
    private double arrivalLng, arrivalLat;

    private Time arrivalTime;

    private String memo;

    @CreatedDate
    private LocalDateTime regdate;

}

