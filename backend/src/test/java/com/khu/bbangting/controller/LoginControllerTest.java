//package com.khu.bbangting.controller;
//
//import com.khu.bbangting.dto.UserFormDto;
//import com.khu.bbangting.model.User;
//import com.khu.bbangting.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//
//@SpringBootTest
//class LoginControllerTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    private MockMvc mvc;
//
//    @BeforeEach
//    public void setup(){
//        mvc = MockMvcBuilders
//                .webAppContextSetup(this.context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    public User createUser(String email, String password){
//        UserFormDto userFormDto = new UserFormDto();
//        userFormDto.setEmail(email);
//        userFormDto.setUsername("고혜연");
//        userFormDto.setPassword(password);
//        User user = User.createUser(userFormDto, passwordEncoder);
//        return userService.saveUser(user);
//    }
//
//    @Test
//    @DisplayName("로그인 성공 테스트")
//    public void loginSuccessTest() throws Exception {
//        // given
//        String email = "test1@gmail.com";
//        String password = "1234qwer";
//        this.createUser(email, password);
//
//        // when
//        mvc.perform(formLogin().userParameter("email")
//                        .loginProcessingUrl("/login")
//                        .user(email).password(password))
//                // then
//                .andExpect(SecurityMockMvcResultMatchers.authenticated());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트")
//    public void loginFailTest() throws Exception {
//        String email = "test2@gmail.com";
//        String password = "1234qwer";
//        this.createUser(email, password);
//
//        mvc.perform(formLogin().userParameter("email")
//                        .loginProcessingUrl("/login")
//                        .user(email).password("12345qwer"))
//                // then
//                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
//    }
//}