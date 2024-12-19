package com.example.demo.common.filter;

import com.example.demo.common.constants.GlobalConstants;
import com.example.demo.common.exception.UnauthorizedException;
import com.example.demo.user.dto.Authentication;
import com.example.demo.user.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@RequiredArgsConstructor
public class AdminRoleFilter implements CommonAuthFilter {

    private final Role role;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = findHttpSession(servletRequest);
           
        Authentication authentication = (Authentication) session.getAttribute(
                GlobalConstants.ADMIN_AUTH);

        Role clientRole = authentication.getRole();
        if (clientRole != this.role) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, role.getName() + " 권한이 필요합니다.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}