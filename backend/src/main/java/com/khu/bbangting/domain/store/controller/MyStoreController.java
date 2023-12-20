package com.khu.bbangting.domain.store.controller;

import com.khu.bbangting.domain.bread.dto.BreadInfoDto;
import com.khu.bbangting.domain.store.dto.MyStoreInfoDto;
import com.khu.bbangting.domain.store.service.MyStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyStoreController {

    @Autowired
    private MyStoreService myStoreService;

    // 마이스토어 페이지 호출
    @GetMapping("/myStore")
    public ResponseEntity<Map<String, Object>> myStorePage(Authentication authentication){
        Map<String, Object> result = new HashMap<>();

        // 스토어 정보 호출
        MyStoreInfoDto myStoreInfoDto = myStoreService.getMyStoreInfo(authentication);
        result.put("myStoreInfo", myStoreInfoDto);

        // 빵 목록 호출
        List<BreadInfoDto> breadInfoList = myStoreService.getBreadList(myStoreInfoDto.getStoreId());
        result.put("breadList", breadInfoList);

        // 오늘의 빵팅 목록 호출
        List<BreadInfoDto> todayTingList = myStoreService.getTodayTingList(myStoreInfoDto.getStoreId());
        result.put("todayTingList", todayTingList);

        return ResponseEntity.ok().body(result);
    }

}
