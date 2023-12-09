package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.error.ResourceNotFoundException;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원정보 및 팔로우 내역 표시
    @Transactional(readOnly = true)
    public User getUserInfo(User user) {

       User getUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", user.getEmail())
        );

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        userResponseDtoList.add(UserResponseDto.builder()
                .nickname(user.getNickname())
                .build()
        );

        return user;
    }

    // 비밀번호 수정
    @Transactional
    public UserResponseDto updatePassword(User user, @Valid PasswordUpdateDto requestDto) {

        String encodePwd = passwordEncoder.encode(requestDto.getNewPassword());
        User updateUser =  userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", user.getEmail())
        );
        updateUser.updatePassword(encodePwd);
        return UserResponseDto.fromUser(updateUser);
    }

    // 닉네임 수정
    @Transactional
    public UserResponseDto updateNickname(User user, @Valid NicknameUpdateDto requestDto) {

        User updateUser =  userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", user.getEmail())
        );
        updateUser.updateNickname(requestDto.getNewNickname());
        return UserResponseDto.fromUser(updateUser);
    }

}
