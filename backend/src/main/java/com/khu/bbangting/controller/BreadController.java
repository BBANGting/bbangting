package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadDetailDto;
import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.service.BreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BreadController {

    @Autowired
    BreadService breadService;

    @GetMapping("/bread/{breadId}")
    public String breadPage(Model model, @PathVariable Long breadId) {
        BreadDetailDto breadDetailDto = breadService.getBreadDetail(breadId);
        log.info(breadDetailDto.toString());
        model.addAttribute("breadDetail", breadDetailDto);

        // 상세정보 페이지 호출 -> model에 담기
        //model.addAttribute("breadDetailPage", );

        return "/bread/detailPage";
    }

    // 상세정보페이지 호출
    @GetMapping("/bread/detail/{breadId}")
    public String detailPage(Model model, @PathVariable Long breadId) {
        return "/bread/detailPage";
    }

/*    // 리뷰 페이지 호출
    @GetMapping("/bread/review/{breadId}")
    public String reviewPage(Model model, @PathVariable Long breadId) {
        return "/bread/reviewPage";
    }*/

    // 문의페이지 호출
    @GetMapping("/bread/inquiry/{breadId}")
    public String inquiryPage(Model model, @PathVariable Long breadId) {
        return "/bread/inquiryPage";
    }
}