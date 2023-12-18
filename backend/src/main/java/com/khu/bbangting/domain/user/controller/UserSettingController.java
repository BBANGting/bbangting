package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.config.jwt.SecurityUtils;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.MyPageService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final UserRepository userRepository;
    private final MyPageService myPageService;

    // 비밀번호 수정하기
    @PostMapping("/myPage/rePassword")
    public ResponseEntity<UserResponseDto> updatePassword(@RequestBody PasswordUpdateDto requestDto) {

        String userEmail = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserResponseDto updatePassword = myPageService.updatePassword(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatePassword);
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/reNickname")
    public ResponseEntity<UserResponseDto> updateNickname(@RequestBody NicknameUpdateDto requestDto) {

        String userEmail = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserResponseDto updateNickname = myPageService.updateNickname(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateNickname);
    }

}
