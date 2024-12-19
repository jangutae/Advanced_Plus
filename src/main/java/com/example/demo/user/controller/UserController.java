package com.example.demo.user.controller;

import com.example.demo.common.constants.GlobalConstants;
import com.example.demo.user.dto.Authentication;
import com.example.demo.user.dto.LoginRequestDto;
import com.example.demo.user.dto.UserRequestDto;
import com.example.demo.user.dto.UserResponseDto;
import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signupWithEmail(@RequestBody UserRequestDto userRequestDto) {

        return ResponseEntity.ok().body(userService.signupWithEmail(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication> loginWithEmail(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest request
    ) {
        Authentication authentication = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.USER_AUTH, authentication);

        return ResponseEntity.ok().body(authentication);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().body("정상적으로 로그아웃 되었습니다.");
    }
}
