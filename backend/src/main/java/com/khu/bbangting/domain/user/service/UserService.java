package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.Role;
import com.khu.bbangting.domain.user.model.Type;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User joinUser(UserJoinFormDto userJoinFormDto) {

        User newUser = saveNewUser(userJoinFormDto);

        return newUser;
    }

    private User saveNewUser(UserJoinFormDto userJoinFormDto) {

        String rawPassword = userJoinFormDto.getPassword(); // encoding 전 비밀번호
        String encPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .email(userJoinFormDto.getEmail())
                .password(encPassword)
                .username(userJoinFormDto.getUsername())
                .nickname(userJoinFormDto.getNickname())
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public UserResponseDto updatePassword(Long userId, @Valid PasswordUpdateDto requestDto) {

//        user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
//        userRepository.save(user);

        return userRepository.findById(userId).map(user -> {
            User updateUser = user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));

            return UserResponseDto.fromUser(updateUser);
        }).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }



    @Transactional
    public UserResponseDto updateNickname(Long userId, @Valid NicknameUpdateDto requestDto) {

//        user.updateNickname(requestDto.getNewNickname());
//        userRepository.save(user);

        return userRepository.findById(userId).map(user -> {
            User updateUser = user.updateNickname(requestDto.getNewNickname());

            return UserResponseDto.fromUser(updateUser);
        }).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}