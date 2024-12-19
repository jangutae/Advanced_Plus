package com.example.demo.common.interceptor;

import com.example.demo.common.constants.GlobalConstants;
import com.example.demo.common.exception.UnauthorizedException;
import com.example.demo.user.dto.Authentication;
import com.example.demo.user.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "세션이 끊어졌습니다.");
        }

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        Role role = authentication.getRole();

        if (role != Role.USER) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "user 권한이 필요합니다.");
        }

        return true;
    }
}
