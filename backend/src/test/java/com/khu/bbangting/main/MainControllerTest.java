package com.khu.bbangting.main;

import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        UserJoinFormDto userJoinFormDto = new UserJoinFormDto();
        userJoinFormDto.setUsername("hyeyeon");
        userJoinFormDto.setNickname("yeonii");
        userJoinFormDto.setEmail("test@gmail.com");
        userJoinFormDto.setPassword("test1234!");
        userService.joinUser(userJoinFormDto);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 로그인: 성공")
    void login_with_email() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "test@gmail.com")
                        .param("password", "test1234!")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인: 실패")
    void login_fail() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "test@naver.com")
                        .param("password", "test1234!")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/loginForm"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("로그아웃: 성공")
    void logout() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}