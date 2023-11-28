package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadSaleDto;
import com.khu.bbangting.service.BreadService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BreadController {

    @Autowired
    private BreadService breadService;

    @GetMapping("/bread/{breadId}")
    public ResponseEntity<Map<String, Object>> breadPage(Model model, @PathVariable Long breadId) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 빵 판매 정보
            BreadSaleDto breadSaleDto = breadService.getBreadSaleInfo(breadId);
            result.put("breadDetail", breadSaleDto);

            // 상세정보 페이지 호출
            List<String> detailInfo = breadService.getInfo(breadId);
            result.put("breadDetailPage", detailInfo);

        } catch (EntityNotFoundException e) {
            result.put("errorMessage", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(result);
    }

    // 상세정보페이지 호출
    @GetMapping("/bread/detail/{breadId}")
    public ResponseEntity<List<String>> detailPage(@PathVariable Long breadId) {
        List<String> detailInfo = breadService.getInfo(breadId);

        return ResponseEntity.ok().body(detailInfo);
    }

/*    // 리뷰 페이지 호출
    @GetMapping("/bread/review/{breadId}")
    public String reviewPage(@PathVariable Long breadId) {
        return "/bread/reviewPage";
    }*/

    // 문의페이지 호출
    @GetMapping("/bread/inquiry/{breadId}")
    public ResponseEntity<String> inquiryPage(@PathVariable Long breadId) {
        return ResponseEntity.ok().body("빵 문의 페이지");
    }
}
