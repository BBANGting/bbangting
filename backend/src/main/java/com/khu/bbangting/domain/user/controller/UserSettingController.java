package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserUpdateDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final UserRepository userRepository;
    private final UserService userService;

    // 비밀번호 수정하기
    @PostMapping("/myPage/{userId}/rePassword")
    public ResponseEntity<UserUpdateDto> updatePassword(@PathVariable Long userId, @RequestBody PasswordUpdateDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return ResponseEntity.ok(userService.updatePassword(user, request));
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/{userId}/reNickname")
    public ResponseEntity<UserUpdateDto> updateNickname(@PathVariable Long userId, @RequestBody NicknameUpdateDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return ResponseEntity.ok(userService.updateNickname(user, request));

    }

}
