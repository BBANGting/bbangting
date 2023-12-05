package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final UserRepository userRepository;
    private final UserService userService;

    // 비밀번호 수정하기
    @PostMapping("/myPage/{userId}/rePassword")
    public ResponseEntity<?> passwordUpdateForm(@PathVariable Long userId, @RequestBody PasswordUpdateDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userService.updatePassword(userId, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/{userId}/reNickname")
    public ResponseEntity<?> updateNickname(@PathVariable Long userId, @RequestBody NicknameUpdateDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userService.updateNickname(userId, requestDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
