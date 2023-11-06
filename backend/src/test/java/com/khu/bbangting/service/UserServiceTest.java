package com.khu.bbangting.service;

import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Transactional
public class UserServiceTest {

    private final String email = "test@naver.com";
    private final String name = "hyeyeon";
    private final String password = "rhgPdus00@!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("사용자 생성 테스트")
    class JoinUSer {

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @DisplayName("사용자 생성 성공")
            public void 회원가입() throws Exception {
                //given
                User user = User.builder()
                        .username(name)
                        .email(email)
                        .password(password)
                        .build();

                //when
                Long saveID = userService.joinUser(user);
                Optional optional = userRepository.findById(saveID);

                //then
                assertEquals(user, optional.get());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {

            @Test
            @DisplayName("중복 이메일 사용")
            public void 중복회원() throws Exception {
                //given
                User user1 = User.builder()
                        .username(name)
                        .email(email)
                        .password(password)
                        .build();

                User user2 = User.builder()
                        .username("aaa")
                        .email(email)
                        .password(password)
                        .build();

                //when
                try {
                    userService.joinUser(user1);
                } catch (IllegalStateException e) {
                    return;
                }

                //then
                UserEmailDuplicateException exception = assertThrows(UserEmailDuplicateException.class, () ->
                        userService.joinUser(user2));

                //then
                assertThat(exception.getMessage()).isEqualTo("이미 가입된 회원입니다");

            }
        }
    }
}
