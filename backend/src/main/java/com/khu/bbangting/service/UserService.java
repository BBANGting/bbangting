package com.khu.bbangting.service;

import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.Role;
import com.khu.bbangting.model.Type;
import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setEmail(user.getEmail());
        user.setPassword(encPassword);
        user.setUsername(user.getUsername());
        user.setRole(Role.USER);
        user.setType(Type.GENERAL);

        User userEntity = userRepository.save(user);

        return userEntity;
    }

    @Transactional
    public User updateUser(User user) {
        User userEntity = userRepository.save(user);

        return userEntity;
    }

}