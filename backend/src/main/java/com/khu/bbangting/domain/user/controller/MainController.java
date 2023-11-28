package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.bread.service.BreadService;
import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<?> joinForm(Model model) {
        model.addAttribute(new UserJoinFormDto());

        return ResponseEntity.ok().build();
    }

    // 로그인페이지 호출
    @GetMapping("/auth/login")
    public ResponseEntity<?> login() {

        return ResponseEntity.ok().build();
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