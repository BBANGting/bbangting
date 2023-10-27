package com.khu.bbangting.controller;

import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.User;
import com.khu.bbangting.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Json 형태로 객체 데이터 반환
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

    @PostMapping("/join")
    public String userForm(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "user/userForm";
        }

        try {
            User user = User.createUser(userFormDto, passwordEncoder);
            userService.saveUser(user);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }

        return "redirect:/";
    }

    @GetMapping(value="/login")
    public String login() {
        return "/user/userLoginForm";
    }

    @GetMapping(value="/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인하세요.");
        return "/user/userLoginForm";
    }
}
