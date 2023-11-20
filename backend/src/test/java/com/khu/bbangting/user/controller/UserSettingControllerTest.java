package com.khu.bbangting.user.controller;

import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.user.WithUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserSettingControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired UserService userService;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("패스워드 수정 폼")
    @WithUser("test@gmail.com")
    void updatePasswordForm() throws Exception {
        mockMvc.perform(get("/setting/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("setting/password"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("passwordUpdateDto"));
    }

    @Test
    @DisplayName("패스워드 수정: 입력값 정상")
    @WithUser("test@gmail.com")
    void updatePassword() throws Exception {
        mockMvc.perform(post("/setting/password")
                        .param("newPassword", "12341234")
                        .param("newPasswordConfirm", "12341234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("message"));
        User user = userRepository.findByNickname("hyeyeon");
        assertTrue(passwordEncoder.matches("12341234", user.getPassword()));
    }

    @Test
    @DisplayName("패스워드 수정: 입력값 에러(불일치)")
    @WithUser("test@gmail.com")
    void updatePasswordWithNotMatchedError() throws Exception {
        mockMvc.perform(post("/setting/password")
                        .param("newPassword", "12341234")
                        .param("newPasswordConfirm", "12121212")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("setting/password"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("passwordUpdateDto"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("패스워드 수정: 입력값 에러(길이)")
    @WithUser("test@gmail.com")
    void updatePasswordWithLengthError() throws Exception {
        mockMvc.perform(post("/setting/password")
                        .param("newPassword", "1234")
                        .param("newPasswordConfirm", "1234")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("setting/password"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("passwordUpdateDto"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("닉네임 수정 폼")
    @WithUser("test@gmail.com")
    void updateNicknameForm() throws Exception {
        mockMvc.perform(get("/setting/nickname"))
                .andExpect(status().isOk())
                .andExpect(view().name("setting/nickname"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("nicknameUpdateDto"));
    }

    @Test
    @DisplayName("닉네임 수정: 입력값 정상")
    @WithUser("test@gmail.com")
    void updateNickname() throws Exception {
        String newNickname = "hyeyeon2";
        mockMvc.perform(post("/setting/nickname")
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/myPage"))
                .andExpect(flash().attributeExists("message"));
        User user = userRepository.findByNickname(newNickname);
        assertEquals(newNickname, user.getNickname());
    }

    @Test
    @DisplayName("닉네임 수정: 에러(길이)")
    @WithUser("test@gmail.com")
    void updateNicknameWithShortNickname() throws Exception {
        String newNickname = "hyeyeonhyeyeon";
        mockMvc.perform(post("/setting/nickname")
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("setting/nickname"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("nicknameUpdateDto"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("닉네임 수정: 에러(중복)")
    @WithUser("test@gmail.com")
    void updateNicknameWithDuplicatedNickname() throws Exception {
        String newNickname = "hyeyeon";
        mockMvc.perform(post("/setting/nickname")
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("setting/nickname"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("nicknameUpdateDto"))
                .andExpect(model().attributeExists("user"));
    }

}
