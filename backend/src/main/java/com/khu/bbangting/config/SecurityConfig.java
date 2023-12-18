package com.khu.bbangting.config;

import com.khu.bbangting.config.jwt.CustomAuthenticationFilter;
import com.khu.bbangting.config.jwt.CustomAuthorizationFilter;
import com.khu.bbangting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationConfiguration authConfig;
    private final CustomAuthorizationFilter customAuthorizationFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/refresh")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/store/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/comingSoon/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/bread/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/myStore/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/myPage/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/review/**")).hasRole("USER")
                        .anyRequest().authenticated())
                    .addFilter(customAuthenticationFilter)
                    .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager()
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
