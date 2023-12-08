package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.model.UserDetailsImpl;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final FollowService followService;

    // 회원정보 및 팔로우 내역 표시
    @Transactional(readOnly = true)
    public User getUserInfo(UserDetailsImpl userDetails) {

        User user = userDetailsService.findById(userDetails.getId());
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        userResponseDtoList.add(UserResponseDto.builder()
                .nickname(user.getNickname())
                .build()
        );

        return user;
    }

    // 비밀번호 수정
    @Transactional
    public UserResponseDto updatePassword(UserDetailsImpl userDetails, @Valid PasswordUpdateDto requestDto) {

//        user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
//        userRepository.save(user);

        return userRepository.findById(userDetails.getId()).map(user -> {
            User updateUser = user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));

            return UserResponseDto.fromUser(updateUser);
        }).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    // 닉네임 수정
    @Transactional
    public UserResponseDto updateNickname(UserDetailsImpl userDetails, @Valid NicknameUpdateDto requestDto) {

//        user.updateNickname(requestDto.getNewNickname());
//        userRepository.save(user);

        return userRepository.findById(userDetails.getId()).map(user -> {
            User updateUser = user.updateNickname(requestDto.getNewNickname());

            return UserResponseDto.fromUser(updateUser);
        }).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
