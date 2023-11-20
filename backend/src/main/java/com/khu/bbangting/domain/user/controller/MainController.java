package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.bread.service.BreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final BreadService breadService;

    // 메인페이지 호출
    @GetMapping("/")
    public String main(Model model) {
        List<BreadInfoDto> todayTingBreadList = breadService.getTodayTing();
        log.info(todayTingBreadList.toString());
        model.addAttribute("todayTingBread", todayTingBreadList);

        return "index";
    }

    // 회원가입페이지 호출
    @GetMapping("/auth/join")
    public String joinForm(Model model) {
        model.addAttribute(new UserJoinFormDto());

        return "user/joinForm";
    }

    // 로그인페이지 호출
    @GetMapping("/auth/login")
    public String login() {

        return "user/loginForm";
    }

    // 오픈예정페이지 호출
    @GetMapping("/comingSoon")
    public String openLineUp(Model model) {
        List<BreadInfoDto> breadOpenLineUpList = breadService.getOpenLineUp();
        log.info(breadOpenLineUpList.toString());
        model.addAttribute("openLineUp",breadOpenLineUpList);

        return "/comingSoonPage";
    }

}