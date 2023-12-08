package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final MyPageService myPageService;

    // 비밀번호 수정하기
    @PostMapping("/myPage/rePassword")
    public ResponseEntity<UserResponseDto> updatePassword(
            @AuthenticationPrincipal User user,
            @RequestBody PasswordUpdateDto requestDto) {
        UserResponseDto updatePassword = myPageService.updatePassword(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatePassword);
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/reNickname")
    public ResponseEntity<UserResponseDto> updateNickname(
            @AuthenticationPrincipal User user,
            @RequestBody NicknameUpdateDto requestDto) {
        UserResponseDto updateNickname = myPageService.updateNickname(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateNickname);
    }

}
