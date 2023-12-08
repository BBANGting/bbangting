package com.khu.bbangting.security;

import com.khu.bbangting.security.jwt.JwtAuthenticationEntryPoint;
import com.khu.bbangting.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/store/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/comingSoon/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/bread/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/myStore/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/myPage/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/review/**")).hasRole("USER")
                        .anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .exceptionHandling((exception)-> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // 인증되지 않은 사용자가 요청했을 때 동작
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
