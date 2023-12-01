package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadSaleDto;
import com.khu.bbangting.service.BreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bread")
public class BreadController {

    @Autowired
    private BreadService breadService;

    // 빵 상세페이지 호출
    @GetMapping("/{breadId}")
    public ResponseEntity<Map<String, Object>> breadPage(@PathVariable Long breadId) {
        Map<String, Object> result = new HashMap<>();

        // 빵 판매 정보
        BreadSaleDto breadSaleDto = breadService.getBreadSaleInfo(breadId);
        result.put("breadDetail", breadSaleDto);

        // 빵 상세 정보 페이지 호출
        List<String> detailInfo = breadService.getInfo(breadId);
        result.put("breadDetailPage", detailInfo);

        return ResponseEntity.ok().body(result);
    }

    // 빵 상세 정보페이지 호출
    @GetMapping("/detail/{breadId}")
    public ResponseEntity<List<String>> detailPage(@PathVariable Long breadId) {
        List<String> detailInfo = breadService.getInfo(breadId);

        return ResponseEntity.ok().body(detailInfo);
    }

    // 리뷰 페이지 호출
    @GetMapping("/review/{breadId}")
    public String reviewPage(@PathVariable Long breadId) {
        List<ReviewFormDto> reviewListDto = reviewService.getListOfBread(breadId);

        return ResponseEntity.ok(reviewListDto);
    }

    // 문의페이지 호출
    @GetMapping("/inquiry/{breadId}")
    public ResponseEntity<String> inquiryPage(@PathVariable Long breadId) {
        return ResponseEntity.ok().body("빵 문의 페이지");
    }
}
