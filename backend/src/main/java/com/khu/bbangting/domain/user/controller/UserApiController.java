package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.*;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/auth/join")
    public ResponseEntity<String> joinUser(@RequestBody JoinRequestDto joinRequestDto) {
        userService.join(joinRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginRequestDto requestDto) {
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping("/auth/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

}
