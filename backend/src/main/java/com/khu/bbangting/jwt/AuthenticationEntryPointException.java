package com.khu.bbangting.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.bbangting.domain.user.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException{
        System.out.println("AuthenticationEntryPointException 진입");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        ResponseDto.fail("Bad_REQUEST", "로그인이 필요합니다.")
                )
        );

        System.out.println(authenticationException);
        System.out.println(authenticationException.getCause());
        System.out.println(authenticationException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
