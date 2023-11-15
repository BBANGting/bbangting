package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadInfoDto;
import com.khu.bbangting.service.BreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private BreadService breadService;

    // 메인페이지 호출
    @GetMapping("/")
    public String main(Model model) {
        List<BreadInfoDto> todayTingBreadList = breadService.getTodayTing();
        log.info(todayTingBreadList.toString());
        model.addAttribute("todayTingBread", todayTingBreadList);

        return "/";
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
