package com.khu.bbangting.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.bbangting.domain.user.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerException implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        System.out.println("AccessDeniedHandlerExcaprtion 진입");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        ResponseDto.fail("BAD_REQUEST", "권한이 없습니다.")
                )
        );
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
