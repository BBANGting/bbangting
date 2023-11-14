package com.khu.bbangting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 1. 메인페이지
    @GetMapping("/")
    public String main(){
        return "main";
    }

    // 2. 로그인 페이지
    @GetMapping("/auth/login")
    public String login() {
        return "/auth/loginForm";
    }

    // 3. 회원가입 페이지
    @GetMapping("/auth/join")
    public String join() {
        return "/auth/joinForm";
    }

    // 4. 오픈예정 페이지
    @GetMapping("/comingsoon")
    public String comingsoon() {
        return "/comingsoonForm";
    }

    // 5. 스토어 페이지
    @GetMapping("/store")
    public String store() {
        return "/storeForm";
    }
}
