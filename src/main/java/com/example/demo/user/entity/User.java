package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status; // NORMAL, BLOCKED

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    public User(String role, String email, String nickname, String password) {
        this.role = Role.of(role);
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public User() {}

    public void updateStatusToBlocked() {
        this.status = UserStatus.BLOCKED;
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

}