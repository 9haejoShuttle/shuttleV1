package com.shuttle.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Admin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String name;

    private String password;

    private boolean enable;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
        enable = true;
    }
}
