package com.khu.bbangting.controller.api;

import com.khu.bbangting.dto.user.UpdateUserDto;
import com.khu.bbangting.dto.user.UserFormDto;
import com.khu.bbangting.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private AuthenticationManager authenticationManager;

    /**
     * 회원가
     */
    @PostMapping("/auth/joinProc")
    public String joinUser(@Valid UserFormDto userFormDto, BindingResult bindingResult) {

        System.out.println("UserApiController: save 함수 호출됨");

        if (bindingResult.hasErrors()) {
            return "auth/joinForm";
        }

        userService.joinUser(userFormDto);

        return "redirect:/";
    }

    /**
     * 회원정보 수정
     */
    @PutMapping("/user/{userId}")
    public ResponseEntity<Long> updateUser(@PathVariable("userId") Long userId,
                                           @RequestBody @Valid UpdateUserDto updateUserDto) {

        return new ResponseEntity<>(userService.updateUser(userId, updateUserDto).getId(), HttpStatus.CREATED);
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
