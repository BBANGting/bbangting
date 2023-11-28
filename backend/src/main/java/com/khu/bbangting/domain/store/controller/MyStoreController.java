package com.khu.bbangting.domain.store.controller;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.store.dto.MyStoreInfoDto;
import com.khu.bbangting.domain.store.service.MyStoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyStoreController {

    @Autowired
    private MyStoreService myStoreService;

    @Autowired
    private FollowService followService;

    // 마이스토어 페이지 호출
    @GetMapping("/myStore/{userId}")
    public String myStorePage(@PathVariable Long userId, Model model){

        try {
            // 스토어 정보 호출
            MyStoreInfoDto myStoreInfoDto = myStoreService.getMyStoreInfo(userId);
            log.info(myStoreInfoDto.toString());
            model.addAttribute("myStoreInfo", myStoreInfoDto);

            // 빵 목록 호출
            List<BreadInfoDto> breadInfoList = myStoreService.getBreadList(myStoreInfoDto.getStoreId());
            log.info(breadInfoList.toString());
            model.addAttribute("breadList", breadInfoList);

            // 오늘의 빵팅 목록 호출
            List<BreadInfoDto> todayTingList = myStoreService.getTodayTingList(myStoreInfoDto.getStoreId());
            log.info(todayTingList.toString());
            model.addAttribute("todayTingList", todayTingList);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "해당 제품을 찾을 수 없습니다.");
            return "myStore/";
        }

        return "myStore/";
    }

}