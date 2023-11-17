package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadFormDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BreadApiController {

    @Autowired
    private BreadService breadService;

    @PostMapping("myStore/bread/new")
    public String createBread(Model model, @Valid @RequestPart BreadFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
        }

        // 대표이미지 등록 안할 시, errorMessage 담기
        if(imageFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage", "대표이미지는 필수 입력 값 입니다.");
            return "myStore/bread/breadForm";
        }

        try {
            breadService.saveBread(requestDto, imageFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "myStore/bread/breadForm";
        }

        return "redirect:myStore/";
    }

    @DeleteMapping("myStore/bread/{breadId}")
    public String deleteBread(@PathVariable Long breadId) {

        breadService.deleteBread(breadId);

        return "redirect:myStore/";
    }

    @GetMapping("myStore/bread/edit/{breadId}")
    public String updateBreadPage(@PathVariable Long breadId, Model model) {

        try {
            BreadFormDto breadFormDto = breadService.getBreadForm(breadId);
            log.info(breadFormDto.toString());
            model.addAttribute("breadFormDto", breadFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "해당 제품을 찾을 수 없습니다.");
            return "myStore/";
        }

        return "myStore/bread/breadFrom";
    }

    @PostMapping("myStore/bread/edit/{breadId}")
    public String updateBread(@PathVariable Long breadId, @Valid @RequestPart BreadFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return "myStore/bread/breadFrom/binding";
        }

        try {
            breadService.updateBread(breadId, requestDto, imageFileList);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("빵 수정 실패", "이미 등록된 제품명입니다.");
            return "myStore/bread/breadForm/data";
        } catch (Exception e) {
            bindingResult.reject("빵 수정 실패", e.getMessage());
            return "myStore/bread/breadForm/ex";
        }

        return "redirect:myStore/";
    }
}
