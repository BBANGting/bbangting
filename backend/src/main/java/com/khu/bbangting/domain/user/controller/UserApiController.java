package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.bread.service.BreadService;
import com.khu.bbangting.domain.user.dto.*;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.AuthService;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthService authService;
    private final BreadService breadService;
    private final UserRepository userRepository;

    private final long COOKIE_EXPIRATION = 172800; // 2일

    // 회원가입
    @PostMapping("/auth/join")
    public ResponseEntity<String> joinUser(@RequestBody JoinRequestDto joinRequestDto) {
        userService.join(joinRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    // 로그인 -> 토큰 발급
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        // User 등록 및 Refresh Token 저장
        UserTokenDto tokenDto = authService.login(requestDto);

        // RT 저장
        HttpCookie httpCookie = ResponseCookie.from("Refresh-token", tokenDto.getRefreshToken())
                .maxAge(COOKIE_EXPIRATION)
                .httpOnly(true)
                .secure(true)
                .build();

        // 로그인 유저 정보
        User userDetail = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 로그인 시 -> 팔로우한 스토어의 당일 오픈 빵팅 알림 보내기
        breadService.loginNotification(userDetail);

        // 로그인 시 -> userId, username 반환
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .userId(userDetail.getId())
                .username(userDetail.getUsername())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .body(responseDto);
    }

    @PostMapping("/checkToken")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
        if (!authService.validate(requestAccessToken)) {
            return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
        }
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> reissue(@CookieValue(name = "Refresh-token") String requestRefreshToken,
                                     @RequestHeader("Authorization") String requestAccessToken) {
        UserTokenDto reissuedTokenDto = authService.reissue(requestAccessToken, requestRefreshToken);

        if (reissuedTokenDto != null) { // 토큰 재발급 성공
            // RT 저장
            ResponseCookie responseCookie = ResponseCookie.from("Refresh-token", reissuedTokenDto.getRefreshToken())
                    .maxAge(COOKIE_EXPIRATION)
                    .httpOnly(true)
                    .secure(true)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    // AT 저장
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
                    .build();

        } else { // Refresh Token 탈취 가능성
            // Cookie 삭제 후 재로그인 유도
            ResponseCookie responseCookie = ResponseCookie.from("Refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();
        }
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
        authService.logout(requestAccessToken);
        ResponseCookie responseCookie = ResponseCookie.from("Refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

}
