package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원정보 및 팔로우 내역 표시
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUserInfo(Authentication authentication) {

        User getUser = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        userResponseDtoList.add(UserResponseDto.builder()
                .nickname(getUser.getNickname())
                .banCount(getUser.getBanCount())
                .build());

        return userResponseDtoList;
    }

    // 비밀번호 수정
    @Transactional
    public UserResponseDto updatePassword(Authentication authentication, @Valid PasswordUpdateDto requestDto) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String encodePwd = passwordEncoder.encode(requestDto.getNewPassword());
        user.updatePassword(encodePwd);
        return UserResponseDto.fromUser(user);
    }

    // 닉네임 수정
    @Transactional
    public UserResponseDto updateNickname(Authentication authentication, @Valid NicknameUpdateDto requestDto) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateNickname(requestDto.getNewNickname());
        return UserResponseDto.fromUser(user);
    }

}
