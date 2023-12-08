package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.JoinRequestDto;
import com.khu.bbangting.domain.user.dto.LoginRequestDto;
import com.khu.bbangting.domain.user.dto.ResponseDto;
import com.khu.bbangting.domain.user.model.UserDetailsImpl;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    // 회원가입
    @PostMapping("/auth/join")
    public ResponseDto<?> joinUser(@RequestBody @Valid JoinRequestDto joinRequestDto) {

        return userService.joinUser(joinRequestDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) throws IOException {
        return userService.loginUser(requestDto, response);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseDto<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.logout(userDetails);
    }

    // 액세스 토큰 재발급
    @GetMapping(value = "/refresh")
    public ResponseDto<?> validate(HttpServletRequest request) throws io.jsonwebtoken.io.IOException {
        return ResponseDto.success(tokenProvider.validateRefreshToken(request));
    }
}
