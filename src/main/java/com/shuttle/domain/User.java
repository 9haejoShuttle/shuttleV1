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

    @Column(unique = true)
    private String phone;

    @Column(nullable = true)
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
        this.role = Role.USER;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void setEnable(boolean enable) {
        this.enable = false;
    }

    public User updateName(String name) {
        this.name = name;

        return this;
    }
}
