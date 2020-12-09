package com.shuttle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
*   사용자의 로그인 이력을 관리하는 테이블
* */
@Getter @Setter
@Entity
public class UserDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime loginDate;

    public UserDetail() {
        this.loginDate = LocalDateTime.now();
    }

    public UserDetail(User user) {
        this.user = user;
        this.loginDate = LocalDateTime.now();
    }


}
