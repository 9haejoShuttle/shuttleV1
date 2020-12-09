package com.shuttle.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = true)
    private String forgotPasswordToken;

    @Column(nullable = true)
    private boolean tokenVerified;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserDetail> userDetail = new HashSet<>();

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

    public void addUserDetail(UserDetail userDetail) {  //로그인 시 UserDetail 자동 추가
        this.getUserDetail().add(userDetail);
        userDetail.setUser(this);
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

    public void setForgotPasswordToken(String token) {
        this.forgotPasswordToken = token;
    }

    public void setTokenVerified(boolean tokenVerified) {
        this.tokenVerified = tokenVerified;
    }
}
