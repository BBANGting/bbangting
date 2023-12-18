package com.khu.bbangting.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.khu.bbangting.config.jwt.JwtConstants.JWT_SECRET;
import static com.khu.bbangting.config.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        // login or refresh 요청이면 토큰 검사 x
        if (servletPath.equals("/auth/login") || servletPath.equals("/refresh") || servletPath.equals("/auth/join")
                || servletPath.equals("/") || servletPath.equals("/store") || servletPath.equals("/bread") || servletPath.equals("/comingSoon")) {
            filterChain.doFilter(request, response);
        } else if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            // 토큰값이 없거나 정상적이지 않다면 400 오류
            log.info("CustomAuthorizationFilter: JWT Token이 존재하지 않습니다");
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            CustomException customException = new CustomException(ErrorCode.TOKEN_NOT_FOUND);
            new ObjectMapper().writeValue(response.getWriter(), customException);
        } else {
            try {
                String accessToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
                DecodedJWT decodedJWT = verifier.verify(accessToken);
                List<String> strAuthorities = decodedJWT.getClaim("roles").asList(String.class);
                List<SimpleGrantedAuthority> authorities = strAuthorities.stream().map(SimpleGrantedAuthority::new).toList();
                String username = decodedJWT.getSubject();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (TokenExpiredException e) {
                log.info("CustomAuthorizationFilter : Access Token이 만료되었습니다.");
                response.setStatus(SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                CustomException customException = new CustomException(ErrorCode.EXPIRED_TOKEN);
                new ObjectMapper().writeValue(response.getWriter(), customException);
            } catch (Exception e) {
                log.info("CustomAuthorizationFilter : JWT 토큰이 잘못되었습니다. message : {}", e.getMessage());
                response.setStatus(SC_BAD_REQUEST);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                CustomException customException = new CustomException(ErrorCode.INVALID_TOKEN);
                new ObjectMapper().writeValue(response.getWriter(), customException);
            }
        }
    }
}
