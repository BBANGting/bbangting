package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadInfoDto;
import com.khu.bbangting.service.BreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private BreadService breadService;

    // 메인페이지 호출
    @GetMapping("/")
    public ResponseEntity<List<BreadInfoDto>> main(Model model) {
        List<BreadInfoDto> todayTingBreadList = breadService.getTodayTing();
        return ResponseEntity.ok(todayTingBreadList);
    }

    // 오픈예정페이지 호출
    @GetMapping("/comingSoon")
    public ResponseEntity<List<BreadInfoDto>> openLineUp(Model model) {
        List<BreadInfoDto> breadOpenLineUpList = breadService.getOpenLineUp();
        return ResponseEntity.ok(breadOpenLineUpList);
    }

}
