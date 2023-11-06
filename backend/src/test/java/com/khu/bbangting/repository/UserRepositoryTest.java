package com.khu.bbangting.repository;

import com.khu.bbangting.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UserRepositoryTest {

    private final String name = "hyeyeon";
    private final String password = "rhgPdus00@!";

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 테스트")
    public void 사용자_저장() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            User user = User.builder()
                    .email("user" + i + "@gmail.com")
                    .username("사용자" + i)
                    .password(password)
                    .build();

            userRepository.save(user);
        });
    }
}
