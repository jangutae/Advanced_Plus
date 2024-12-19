package com.example.demo.user.dto;

import com.example.demo.user.entity.Role;
import com.example.demo.user.entity.User;

public class UserResponseDto {

    private final Role role;
    private final String email;
    private final String nickname;
    private final String password;

    public UserResponseDto(Role role, String email, String nickname, String password) {
        this.role = role;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getRole(),
                user.getEmail(),
                user.getNickname(),
                user.getPassword());
    }
}
