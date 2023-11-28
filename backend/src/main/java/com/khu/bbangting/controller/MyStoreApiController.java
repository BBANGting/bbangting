package com.khu.bbangting.controller;

import com.khu.bbangting.dto.StoreFormDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
// 마이스토어 c,r,u,d api
public class MyStoreApiController {

    @Autowired
    private MyStoreService myStoreService;

    @PostMapping("myStore/new")
    public String createMyStore(Model model, @Valid @RequestPart StoreFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList){

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
        }

        // 스토어 로고 등록 안할 시, errorMessage 담기
        if(imageFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage", "스토어 로고는 필수 입력 값 입니다.");
            return "myStore/storeForm";
        }

        try {
            myStoreService.saveStore(requestDto, imageFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "스토어 등록 중 에러가 발생하였습니다.");
            return "myStore/storeForm";
        }

        return "redirect:/myStore";    // 마이스토어 페이지로 리다이렉트
    }

    @DeleteMapping("myStore/{userId}")
    public String deleteMyStore(@PathVariable Long userId) {

        myStoreService.deleteStore(userId);

        return "redirect:/myStore/none";      // 마이스토어 등록 안된 상태의 페이지
    }

    @GetMapping("myStore/edit/{userId}")
    public String updateMyStorePage(@PathVariable Long userId, Model model) {

        try {
            StoreFormDto storeFormDto = myStoreService.getStoreForm(userId);
            log.info(storeFormDto.toString());
            model.addAttribute("storeFormDto", storeFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "해당 제품을 찾을 수 없습니다.");
            return "/myStore";
        }

        return "myStore/storeForm";
    }

    @PostMapping("myStore/edit/{userId}")
    public String updateMyStore(@PathVariable Long userId, @Valid @RequestPart StoreFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return "myStore/storeForm";
        }

        try {
            myStoreService.updateStore(userId, requestDto, imageFileList);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("스토어 수정 실패", "이미 존재하는 스토어입니다.");
            return "myStore/storeForm";
        } catch (Exception e) {
            bindingResult.reject("스토어 수정 실패", e.getMessage());
            return "myStore/storeForm";
        }

        return "redirect:myStore/";
    }
}
