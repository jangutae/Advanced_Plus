package com.example.demo.common.config;

import com.example.demo.common.filter.AdminRoleFilter;
import com.example.demo.common.filter.AuthFilter;
import com.example.demo.common.filter.UserRoleFilter;
import com.example.demo.common.interceptor.AdminRoleInterceptor;
import com.example.demo.common.interceptor.AuthInterceptor;
import com.example.demo.common.interceptor.UserRoleInterceptor;
import com.example.demo.user.entity.Role;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    // TODO: 2. 인가에 대한 이해
    private static final String[] AUTH_REQUIRED_PATH_PATTERNS = {"/users/logout", "/admins/login",};
    private static final String[] USER_ROLE_REQUIRED_PATH_PATTERNS = {"/reservations/*", "/users/*"};
    private static final String[] ADMIN_ROLE_REQUIRED_PATH_PATTERNS = {"/admins/*"};

    private final AuthInterceptor authInterceptor;
    private final UserRoleInterceptor userRoleInterceptor;
    private final AdminRoleInterceptor adminRoleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(AUTH_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE);

        registry.addInterceptor(adminRoleInterceptor)
                .addPathPatterns(ADMIN_ROLE_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE + 1);

        registry.addInterceptor(userRoleInterceptor)
                .addPathPatterns(USER_ROLE_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE + 2);
    }

    @Bean
    public FilterRegistrationBean authFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.addUrlPatterns(AUTH_REQUIRED_PATH_PATTERNS);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean adminRoleFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AdminRoleFilter(Role.ADMIN));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        filterRegistrationBean.addUrlPatterns(ADMIN_ROLE_REQUIRED_PATH_PATTERNS);
        return filterRegistrationBean;
    }


    @Bean
    public FilterRegistrationBean userRoleFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new UserRoleFilter(Role.USER));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        filterRegistrationBean.addUrlPatterns(USER_ROLE_REQUIRED_PATH_PATTERNS);
        return filterRegistrationBean;
    }
}