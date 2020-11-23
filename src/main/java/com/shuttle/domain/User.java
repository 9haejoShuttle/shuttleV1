package com.shuttle.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean enable;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String phone, String name, String password, String email){
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name =name;
        this.enable = true;
    }
}
