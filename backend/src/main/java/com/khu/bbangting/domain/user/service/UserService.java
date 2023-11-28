package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.dto.UserUpdateDto;
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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * 로그인아이디로 유저정보 조회
     */
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

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

//    public User updatePassword(User user, String newPassword) {
//        user.updatePassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//    }

    public UserUpdateDto updatePassword(User user, @Valid PasswordUpdateDto request) {

        User updateUser = user.updatePassword(passwordEncoder.encode(request.getNewPassword()));

        return UserUpdateDto.fromUser(updateUser);
    }

    public UserUpdateDto updateNickname(User user, @Valid NicknameUpdateDto request) {

        User updateUser = user.updateNickname(request.getNewNickname());

        return UserUpdateDto.fromUser(updateUser);
    }

}