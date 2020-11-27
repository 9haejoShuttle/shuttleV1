package com.shuttle.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/*
 *   Remeber-me를 위한 엔티티
 *   org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
 *   클래스에 정의된 테이블 필요
 * */
@Getter @Setter
@Table(name = "persistent_logins")
@Entity
public class PersistentLogins {
    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private LocalDateTime lastUsed;
}
