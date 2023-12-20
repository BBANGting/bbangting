package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.domain.user.dto.JoinRequestDto;
import com.khu.bbangting.domain.user.dto.UserResponseDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto join(JoinRequestDto requestDto) {
        isExistUserEmail(requestDto.getEmail());

        String rawPassword = requestDto.getPassword(); // encoding 전 비밀번호
        String encPassword = passwordEncoder.encode(rawPassword);
        requestDto.setPassword(encPassword);

        User saveUser = userRepository.save(JoinRequestDto.ofEntity(requestDto));

        return UserResponseDto.fromUser(saveUser);
    }

    private void isExistUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

}