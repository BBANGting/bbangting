package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.dto.BreadUpdateFormDto;
import com.khu.bbangting.repository.BreadRepository;
import com.khu.bbangting.service.BreadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
            breadService.save(requestDto);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("빵 등록 실패", "이미 등록된 빵입니다.");
            return "myStore/bread/breadForm";
        } catch (Exception e) {
            bindingResult.reject("빵 등록 실패", e.getMessage());
            return "myStore/bread/breadForm";
        }

        return "redirect:myStore/";
    }

    @DeleteMapping("myStore/bread/{breadId}")
    public String 빵삭제(@PathVariable Long breadId) {

        breadService.delete(breadId);

        return "redirect:myStore/";
    }
}
