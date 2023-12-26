package com.khu.bbangting.domain.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.error.UserException;
import com.khu.bbangting.domain.user.dto.*;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static com.khu.bbangting.config.jwt.JwtConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public UserResponseDto join(JoinRequestDto requestDto) {
        isExistUserEmail(requestDto.getEmail());

        String rawPassword = requestDto.getPassword(); // encoding 전 비밀번호
        String encPassword = passwordEncoder.encode(rawPassword);
        requestDto.setPassword(encPassword);

        User saveUser = userRepository.save(JoinRequestDto.ofEntity(requestDto));

        return UserResponseDto.fromUser(saveUser);
    }

    private void isExistUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // Token
    @Transactional
    public void updateRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateRefreshToken(refreshToken);
    }

    @Transactional
    public Map<String, String> refresh(String refreshToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        long now = System.currentTimeMillis();
        String email = decodedJWT.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // TODO refresh token 만료 시 에러메시지 -> ExceptionHandler에서 처리 TokenExpiredException
        // TODO RT 유효하지 않을 시 에러메시지 -> ExceptionHandler에서 처리 JWTVerificationException
        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(now + AT_EXP_TIME))
                .withClaim("role", user.getRole().toString())
                .sign(Algorithm.HMAC256(JWT_SECRET));

        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급
        long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;

        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        log.info("========= DIFFDAYS : {} =========", diffDays);
        log.info("========= DIFFMIN : {} =========", diffMin);
        log.info("========= refresh Exp Time : {} =========", LocalDateTime.ofInstant(Instant.ofEpochMilli(refreshExpireTime),
                TimeZone.getDefault().toZoneId()));
        log.info("========= now : {} =========", LocalDateTime.ofInstant(Instant.ofEpochMilli(now),
                TimeZone.getDefault().toZoneId()));
        if (diffMin < 5) {
            String newRefreshToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(now + RT_EXP_TIME))
                    .sign(Algorithm.HMAC256(JWT_SECRET));
            accessTokenResponseMap.put(RT_HEADER, newRefreshToken);
            user.updateRefreshToken(newRefreshToken);
        }

        accessTokenResponseMap.put(AT_HEADER, accessToken);

        return accessTokenResponseMap;
    }

}