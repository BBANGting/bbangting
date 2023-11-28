//package com.khu.bbangting.user.controller;
//
//import com.khu.bbangting.error.CustomException;
//import com.khu.bbangting.error.ErrorCode;
//import com.khu.bbangting.domain.user.repository.UserRepository;
//import com.khu.bbangting.domain.user.model.User;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    @DisplayName("회원 가입 화면 진입 확인")
//    void joinForm() throws Exception {
//        mockMvc.perform(get("/auth/join"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/joinForm"))
//                .andExpect(model().attributeExists("userJoinFormDto"));
//    }
//
//    @Test
//    @DisplayName("회원 가입 처리: 입력값 오류")
//    void joinSubmitWithError() throws Exception {
//        mockMvc.perform(post("/auth/join")
//                        .param("username", "hyeyeon")
//                        .param("nickname", "nickname")
//                        .param("email", "test@gmail")
//                        .param("password", "1234!")
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/joinForm"));
//    }
//
//    @Test
//    @DisplayName("회원 가입 처리: 입력값 정상")
//    void joinSubmit() throws Exception {
//        mockMvc.perform(post("/auth/join")
//                        .param("username", "hyeyeon")
//                        .param("nickname", "nickname")
//                        .param("email", "email@gmail.com")
//                        .param("password", "password1234!")
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/"));
//
//        assertTrue(userRepository.existsByEmail("email@gmail.com")); // 메일이 DB에 저장되었는지 확인
//        User user = userRepository.findByEmail("email@gmail.com")
//                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
//        assertNotEquals(user.getPassword(), "password1234!");
//
//    }
//}
