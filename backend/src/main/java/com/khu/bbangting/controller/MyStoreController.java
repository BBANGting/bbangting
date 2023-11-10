package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.service.MyStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyStoreController {

    @Autowired
    private MyStoreService myStoreService;

    // 1. 마이스토어 페이지 호출
    @GetMapping("/myStore/{userId}")
    public String 마이스토어페이지(@PathVariable(name="userId") Long loginMember){
        // 마이스토어 서비스에서 loginMember(userId)로 해당 유저 myStoreRepo에서 존재하는지 찾고,

        // 존재한다면 마이스토어페이지에 띄워야하는 정보들 return

        // 존재하지 않으면, 존재하지 않는다고 값 보냄

        return "/myStore";
    }

    // 2. 마이스토어 등록하기
    @PostMapping("/myStore/new")
    public String 마이스토어등록(@Valid @RequestBody StoreFormDto requestDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
        }

        myStoreService.스토어등록(requestDto);

        return "redirect:/myStore";    // 마이스토어 페이지로 리다이렉트
    }

    @DeleteMapping("/myStore/{userId}")
    public String 마이스토어삭제(@PathVariable Long userId) {

        myStoreService.스토어삭제(userId);

        return "redirect:myStore/none";      // 마이스토어 등록 안된 상태의 페이지
    }


}
