package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final MyPageService myPageService;

    // 비밀번호 수정하기
    @PostMapping("/myPage/rePassword")
    public ResponseEntity<?> updatePassword(
            Authentication authentication,
            @RequestBody PasswordUpdateDto requestDto) {

        myPageService.updatePassword(authentication, requestDto);
        return ResponseEntity.ok().build();
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/reNickname")
    public ResponseEntity<?> updateNickname(
            Authentication authentication,
            @RequestBody NicknameUpdateDto requestDto) {

        myPageService.updateNickname(authentication, requestDto);
        return ResponseEntity.ok().build();
    }

}
