package com.khu.bbangting.domain.bread.controller;

import com.khu.bbangting.domain.bread.dto.BreadDetailDto;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.bread.service.BreadService;
import com.khu.bbangting.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BreadController {

    @Autowired
    BreadService breadService;
    @Autowired
    ReviewService reviewService;

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


     // 리뷰페이지 호출
    @GetMapping("/bread/review/{breadId}")
    public String reviewPage(Model model, @PathVariable Long breadId) {
        List<ReviewFormDto> reviewListDto = reviewService.getListOfBread(breadId);
        log.info(reviewListDto.toString());
        model.addAttribute("reviewDetail", reviewListDto);

        return "/bread/reviewPage";
    }

    // 문의페이지 호출
    @GetMapping("/bread/inquiry/{breadId}")
    public String inquiryPage(Model model, @PathVariable Long breadId) {
        return "/bread/inquiryPage";
    }
}