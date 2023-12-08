package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.*;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/auth/join")
    public ResponseEntity<UserResponseDto> joinUser(@RequestBody JoinRequestDto joinRequestDto) {
        UserResponseDto successUser = userService.join(joinRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody LoginRequestDto requestDto) {
        UserTokenDto loginDto = userService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).header(loginDto.getToken()).body(loginDto);
    }

//    // 로그아웃
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout() {
//        userService.logout();
//        return ResponseEntity.ok().build();
//    }

}
