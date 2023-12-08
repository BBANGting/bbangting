package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.model.UserDetailsImpl;
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
    public ResponseEntity<?> passwordUpdateForm(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordUpdateDto requestDto) {

        myPageService.updatePassword(userDetails, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/reNickname")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody NicknameUpdateDto requestDto) {

        myPageService.updateNickname(userDetails, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
