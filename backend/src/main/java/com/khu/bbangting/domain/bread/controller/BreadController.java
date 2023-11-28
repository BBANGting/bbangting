package com.khu.bbangting.domain.bread.controller;

import com.khu.bbangting.domain.bread.dto.BreadDetailDto;
import com.khu.bbangting.domain.review.dto.ReviewFormDto;
import com.khu.bbangting.domain.bread.service.BreadService;
import com.khu.bbangting.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BreadDetailDto> breadPage(Model model, @PathVariable Long breadId) {
        BreadDetailDto breadDetailDto = breadService.getBreadDetail(breadId);
        log.info(breadDetailDto.toString());
        model.addAttribute("breadDetail", breadDetailDto);

        // 상세정보 페이지 호출
        List<String> detailInfo = breadService.getInfo(breadId);
        log.info(detailInfo.toString());
        model.addAttribute("breadDetailPage", detailInfo);

        return ResponseEntity.ok(breadService.getBreadDetail(breadId));
    }

    // 상세정보페이지 호출
    @GetMapping("/bread/detail/{breadId}")
    public String detailPage(Model model, @PathVariable Long breadId) {
        List<String> detailInfo = breadService.getInfo(breadId);
        log.info(detailInfo.toString());
        model.addAttribute("breadDetailPage", detailInfo);

        return "/bread/detailPage";
    }

     // 리뷰페이지 호출
    @GetMapping("/bread/review/{breadId}")
    public ResponseEntity<?> reviewPage(@PathVariable Long breadId) {
        List<ReviewFormDto> reviewListDto = reviewService.getListOfBread(breadId);

        return ResponseEntity.ok(reviewListDto);
    }

    // 문의페이지 호출
    @GetMapping("/bread/inquiry/{breadId}")
    public String inquiryPage(@PathVariable Long breadId) {
        return "/bread/inquiryPage";
    }
}