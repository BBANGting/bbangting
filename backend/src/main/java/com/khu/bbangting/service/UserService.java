package com.khu.bbangting.service;

import com.khu.bbangting.dto.user.UpdateUserDto;
import com.khu.bbangting.dto.user.UserFormDto;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import com.khu.bbangting.model.Role;
import com.khu.bbangting.model.Type;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public Long joinUser(UserFormDto userFormDto) {
        validUserEmailDuplicate(userFormDto.getEmail());

        return userRepository.save(User.builder()
                .email(userFormDto.getEmail())
                .password(passwordEncoder.encode(userFormDto.getPassword()))
                .username(userFormDto.getUsername())
                .nickname(userFormDto.getNickname())
                .role(Role.USER)
                .type(Type.GENERAL)
                .build()).getId();

    }

    //회원 이메일 중복 검증
    private void validUserEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email))
            throw new CustomException(ErrorCode.DUPLICATED_LOGIN_EMAIL);
    }

    @Transactional
    public User updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateUser(updateUserDto.getNickname(), updateUserDto.getPassword());

        return user;
    }

}