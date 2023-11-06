package com.khu.bbangting.service;

import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public User findUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public void updateUser(Long userId, User user) {
        User tempUser = userRepository.findById(userId).get();

        userRepository.save(tempUser);
    }

}
