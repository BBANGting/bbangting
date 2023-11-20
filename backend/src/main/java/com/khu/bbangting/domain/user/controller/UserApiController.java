package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.controller.validator.UserJoinFormValidator;
import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final UserJoinFormValidator userJoinFormValidator;
    private final UserRepository userRepository;

    @InitBinder("userJoinFormDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userJoinFormValidator);
    }

    /**
     * 회원가입
     */
    @PostMapping("/auth/join")
    public String joinUser(@Valid @ModelAttribute UserJoinFormDto userJoinFormDto, Errors errors) {
        if (errors.hasErrors()) {
            return "user/joinForm";
        }

        userService.joinUser(userJoinFormDto);

        return "redirect:/";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login";
    }

}
