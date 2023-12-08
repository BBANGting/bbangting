package com.khu.bbangting.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.bbangting.domain.user.dto.ResponseDto;
import com.khu.bbangting.domain.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String BEARER_PREFIX = "Bearer";

    public static String AUTHORITIES_KEY = "auth";
    private final String SECRET_KEY;
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("doFiterInternel 진입?");

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println(request);

        String jwt = resolveToken(request);     // request의 jwt 토큰 받기
        System.out.println("jwt: " + jwt);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            System.out.println("1??????");
            Claims claims;
            try {
                claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            } catch (ExpiredJwtException e) {
                claims = e.getClaims();
            }

            if (claims.getExpiration().toInstant().toEpochMilli() < Instant.now().toEpochMilli()) {
                System.out.println("2??????");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(
                        new ObjectMapper().writeValueAsString(
                                ResponseDto.fail("BAD_REQUEST", "Token이 유효햐지 않습니다.")
                        )
                );
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            String subject = claims.getSubject();       // jwt 토큰에서 sub(사용자) 추출
            System.out.println("subject: " + subject);
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            System.out.println("authorities: " + authorities);

            UserDetails principal = userDetailsService.loadUserByUsername(subject);    // 받아온 sub(사용자)로 사용자 정보 반환
            System.out.println("principal: " + principal);

            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
            System.out.println("authentication: " + authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        System.out.println(request);
        System.out.println(response);
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        System.out.println("bearerToken: " + bearerToken);
        System.out.println(StringUtils.hasText(bearerToken));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {    // bearerToken이 유효하고 bearerToken이 BEARER_PREFIX(= "BEARER") 로 시작하면 토큰의 7번째 자리까지 리턴
            System.out.println("진입!!!!!!");
            return bearerToken.substring(7);
        }
        return null;
    }

}