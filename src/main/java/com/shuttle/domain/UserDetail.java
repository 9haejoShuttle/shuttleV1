package com.shuttle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
public class UserDetail {
    @Id
    private Long UserId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @LastModifiedDate
    private LocalDateTime loginDate;

    public UserDetail(User user) {
        this.user = user;
        loginDate = LocalDateTime.now();
    }

}
