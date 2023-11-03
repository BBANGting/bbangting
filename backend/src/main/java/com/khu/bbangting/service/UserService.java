package com.khu.bbangting.service;

import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User findUser(String email) {
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            return new User();
        });

        return user;
    }

    @Transactional
    public void saveUser(UserFormDto userFormDto) {

        String rawPassword = userFormDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        System.out.println(rawPassword);

        User user = User.builder()
                .email(userFormDto.getEmail())
                .password(encPassword)
                .username(userFormDto.getUsername())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void modiUser(UserFormDto userFormDto) {
        User persistance = userRepository.findByEmail(userFormDto.getEmail())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("회원 찾기 실패");
                });

        String rawPassword = userFormDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        persistance.setPassword(encPassword);
    }

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Transactional
//    public void saveUser(UserFormDto userDto) {
//
//        // 중복된 회원정보 존재하는지 확인
//        if(userRepository.existsByEmail(userDto.getEmail())){
//            throw new IllegalStateException("이미 가입된 회원입니다.");
//        }
//
//        User user = User.builder()
//                .email(userDto.getEmail())
//                .password(passwordEncoder.encode(userDto.getPassword()))
//                .username(userDto.getUsername())
//                .build();
//
//        userRepository.save(user);
//    }

}