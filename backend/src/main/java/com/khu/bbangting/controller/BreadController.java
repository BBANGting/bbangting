package com.khu.bbangting.controller;

import com.khu.bbangting.dto.bread.BreadFormDto;
import com.khu.bbangting.dto.bread.BreadUpdateFormDto;
import com.khu.bbangting.service.BreadService;
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
public class BreadController {

    @Autowired
    private BreadService breadService;

    @PostMapping("myStore/bread/new")
    public String 빵등록(@Valid @RequestBody BreadFormDto requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
        }

        // 대표이미지 등록 안할 시, errorMessage 담기
//        if(itemImgFileList.get(0).isEmpty() && myStoreFormDto.getId() == null){
//            model.addAttribute("errorMessage", "대표이미지는 필수 입력 값 입니다.");
//            return "myStore/myStoreForm";
//        }

        try {
            breadService.saveBread(requestDto);
        } catch (Exception e) {
            bindingResult.reject("빵 등록 실패", e.getMessage());
            return "myStore/bread/breadForm";
        }

        return "redirect:myStore/";
    }

    @DeleteMapping("myStore/bread/{breadId}")
    public String 빵삭제(@PathVariable Long breadId) {

        breadService.deleteBread(breadId);

        return "redirect:myStore/";
    }

    @GetMapping("myStore/bread/edit/{breadId}")
    public String 빵수정페이지(@PathVariable Long breadId, Model model) {

        try {
            BreadFormDto breadFormDto = breadService.getBreadForm(breadId);
            log.info(breadFormDto.toString());
            model.addAttribute("breadFormDto", breadFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "해당 제품을 찾을 수 없습니다.");
            return "myStore/bread/breadFrom";
        }

        return "myStore/";
    }

    @PutMapping("myStore/bread/edit/{breadId}")
    public String 빵수정(@Valid @RequestBody BreadUpdateFormDto requestDto, BindingResult bindingResult, @PathVariable Long breadId) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return "myStore/bread/breadFrom";
        }

        try {
            breadService.updateBread(breadId, requestDto);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("빵 수정 실패", "이미 등록된 제품명입니다.");
            return "myStore/bread/breadForm";
        } catch (Exception e) {
            bindingResult.reject("빵 수정 실패", e.getMessage());
            return "myStore/bread/breadForm";
        }

        return "redirect:myStore/";
    }
}