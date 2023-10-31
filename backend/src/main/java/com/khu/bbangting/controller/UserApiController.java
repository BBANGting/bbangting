package com.khu.bbangting.controller;

import com.khu.bbangting.dto.ResponseDto;
import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.User;
import com.khu.bbangting.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //Json 형태로 객체 데이터 반환
public class UserApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/join")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

    // 일단 save 만 수정함!
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody UserFormDto userFormDto) {

        System.out.println("UserApiController: save 함수 호출됨");
        userService.saveUser(userFormDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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
