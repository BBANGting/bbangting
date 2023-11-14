package com.khu.bbangting.controller;

import com.khu.bbangting.service.MyStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    //

}