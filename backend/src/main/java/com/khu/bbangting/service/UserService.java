package com.khu.bbangting.service;

import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.core.userdetails.User.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User saveUser(UserFormDto userDto) {

    //    validateDuplicateUser(userDto.getEmail());

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        return userRepository.save(User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .build());
    }

//    private void validateDuplicateUser(String email) {
//        userRepository.findByEmail(email);
//        if (findUser != null) {
//            throw new IllegalStateException("이미 가입된 회원입니다.");
//        }
//    }

}