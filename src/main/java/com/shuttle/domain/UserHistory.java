package com.shuttle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
public class UserHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_HISTORY_ID")
    Long id;

    Long userId;

    String memo;

    @CreatedDate
    LocalDateTime startDate;

    @LastModifiedDate
    LocalDateTime endDate;
}
