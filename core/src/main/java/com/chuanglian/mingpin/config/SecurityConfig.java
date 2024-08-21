package com.chuanglian.mingpin.config;

import com.chuanglian.mingpin.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // BCrypt加密编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails user = User.withUsername("john")
//                .password("123456")
//                .roles("")
//                .build();
//
//        manager.createUser(user);
//
//        return manager;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 不通过 Session 获取 SecurityContext
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 对于登录接口允许匿名访问
                        .requestMatchers("/login", "/register").anonymous()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated()
//                ).exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(authenticationEntryPoint())
//                        .accessDeniedHandler(accessDeniedHandler())
                );

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            throw new RuntimeException("token非法");
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            throw new RuntimeException("权限不足");
        };
    }
}
