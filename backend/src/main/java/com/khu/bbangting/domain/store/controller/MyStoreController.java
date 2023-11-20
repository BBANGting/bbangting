package com.khu.bbangting.domain.store.controller;

import com.khu.bbangting.domain.store.dto.MyStoreInfoDto;
import com.khu.bbangting.domain.store.service.MyStoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyStoreController {

    @Autowired
    private MyStoreService myStoreService;

    // 마이스토어 페이지 호출
    @GetMapping("/myStore/{userId}")
    public String myStorePage(@PathVariable Long userId, Model model){

        try {
            MyStoreInfoDto myStoreInfoDto = myStoreService.getMyStoreInfo(userId);
            log.info(myStoreInfoDto.toString());
            model.addAttribute("myStoreInfoDto", myStoreInfoDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "해당 제품을 찾을 수 없습니다.");
            return "myStore/";
        }

        return "myStore/";
    }

}