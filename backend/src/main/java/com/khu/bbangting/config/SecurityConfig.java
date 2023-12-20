package com.khu.bbangting.config;

import com.khu.bbangting.config.jwt.JwtAccessDeniedHandler;
import com.khu.bbangting.config.jwt.JwtAuthenticationEntryPoint;
import com.khu.bbangting.config.jwt.JwtAuthenticationFilter;
import com.khu.bbangting.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // ACL(Access Control List, 접근 제어 목록)의 예외 URL 설정
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) // 정적 리소스들
                .requestMatchers(new AntPathRequestMatcher("/"))
                .requestMatchers(new AntPathRequestMatcher("/auth/**"))
                .requestMatchers(new AntPathRequestMatcher("/store/**"))
                .requestMatchers(new AntPathRequestMatcher("/comingSoon/**"))
                .requestMatchers(new AntPathRequestMatcher("/bread/**"));

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // jwt 토큰 사용을 위한 설정
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(login -> login.disable())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X

                // 예외처리
                .exceptionHandling(except -> except
                        // 401에러 핸들링을 위한 설정
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        // 403 에러 핸들링을 위한 설정
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/logout")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/myStore/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/myPage/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/review/**")).hasRole("USER")
                        .anyRequest().authenticated())

                .headers(header -> header
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
