package com.khu.bbangting.controller;

import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.dto.StoreUpdateFormDto;
import com.khu.bbangting.service.MyStoreService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
// 마이스토어 c,r,u,d api
public class MyStoreApiController {

    @Autowired
    private MyStoreService myStoreService;

    @PostMapping("myStore/new")
    public String 마이스토어등록(@Valid @RequestBody StoreFormDto requestDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
        }

        myStoreService.스토어등록(requestDto);

        return "redirect:/myStore";    // 마이스토어 페이지로 리다이렉트
    }

    @DeleteMapping("myStore/{userId}")
    public String 마이스토어삭제(@PathVariable Long userId) {

        myStoreService.스토어삭제(userId);

        return "redirect:myStore/none";      // 마이스토어 등록 안된 상태의 페이지
    }

    @GetMapping("myStore/edit/{userId}")
    public String 마이스토어수정페이지(@PathVariable Long userId, Model model) {

        try {
            StoreFormDto storeFormDto = myStoreService.getStoreForm(userId);
            log.info(storeFormDto.toString());
            model.addAttribute("storeFormDto", storeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "해당 제품을 찾을 수 없습니다.");
            return "myStore/";
        }

        return "myStore/storeForm";
    }

    @PutMapping("myStore/edit/{userId}")
    public String 마이스토어수정(@Valid @RequestBody StoreUpdateFormDto requestDto, BindingResult bindingResult, @PathVariable Long userId) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return "myStore/storeForm";
        }

        try {
            myStoreService.updateStore(userId, requestDto);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("스토어 수정 실패", "이미 존재하는 스토어입니다.");
            return "myStore/bread/breadForm";
        } catch (Exception e) {
            bindingResult.reject("스토어 수정 실패", e.getMessage());
            return "myStore/storeForm";
        }

        return "redirect:myStore/";
    }
}
