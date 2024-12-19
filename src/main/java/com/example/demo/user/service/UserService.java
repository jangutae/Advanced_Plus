package com.example.demo.user.service;

import com.example.demo.common.util.PasswordEncoder;
import com.example.demo.user.dto.Authentication;
import com.example.demo.user.dto.LoginRequestDto;
import com.example.demo.user.dto.UserRequestDto;
import com.example.demo.user.dto.UserResponseDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto signupWithEmail(UserRequestDto requestDto) {
        User user = new User(requestDto.getRole(), requestDto.getEmail(), requestDto.getNickname(), requestDto.getPassword());
        String encodedPassword = PasswordEncoder.encode(user.getPassword());
        user.updatePassword(encodedPassword);

        userRepository.save(user);


        return UserResponseDto.toDto(user);
    }

    public Authentication loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !PasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이름 혹은 잘못된 비밀번호");
        }
        return new Authentication(user.getId(), user.getRole());
    }
}
