package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.service.MyStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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


    // 2. 마이스토어 등록페이지 호출
    @GetMapping("/myStore/new")
    public String myStoreForm(Model model) {
        System.out.println(new BreadFormDto());
        model.addAttribute("StoreFormDto", new StoreFormDto());
        return "myStore/StoreForm";
    }


    // 3. 마이스토어 등록하기
/*    @PostMapping("/myStore/new")
    public String 마이스토어등록(Model model, @Valid StoreFormDto StoreFormDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "myStore/StoreForm";
        }

        try {
            myStoreService.스토어등록(StoreFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "마이스토어 등록 중 에러가 발생하였습니다.");
            return "myStore/StoreForm";
        }

        return "redirect:/myStore";    // 마이스토어 페이지로 리다이렉트
    }*/

    @PostMapping("/myStore/new")
    public String 마이스토어등록(@Valid @RequestBody StoreFormDto StoreFormDto, BindingResult bindingResult) {

        try {
            myStoreService.스토어등록(StoreFormDto);
        } catch (Exception e) {

        }

        return "redirect:/myStore";    // 마이스토어 페이지로 리다이렉트
    }

    // 빵 등록 페이지 호출
    @GetMapping("/myStore/bread/new")
    public String breadForm(Model model) {
        model.addAttribute("breadFormDto", new BreadFormDto());
        return "bread/breadForm";
    }


}
