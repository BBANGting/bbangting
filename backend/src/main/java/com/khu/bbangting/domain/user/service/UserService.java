package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.model.Role;
import com.khu.bbangting.domain.user.model.Type;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User joinUser(UserJoinFormDto userJoinFormDto) {
        User newUser = saveNewUser(userJoinFormDto);

        return newUser;
    }

    private User saveNewUser(UserJoinFormDto userJoinFormDto) {
        User user = User.builder()
                .email(userJoinFormDto.getEmail())
                .password(passwordEncoder.encode(userJoinFormDto.getPassword()))
                .username(userJoinFormDto.getUsername())
                .nickname(userJoinFormDto.getNickname())
                .role(Role.USER)
                .type(Type.GENERAL)
                .build();

        return userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.updatePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void updateNickname(User user, String nickname) {
        user.updateNickname(nickname);
        userRepository.save(user);
    }

}