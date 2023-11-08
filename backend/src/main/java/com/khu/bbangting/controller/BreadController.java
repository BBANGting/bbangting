package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.service.BreadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BreadController {

    @Autowired
    private BreadService breadService;



    // 빵 등록
    @PostMapping("/myStore/bread")
    public String breadNew(Model model, @Valid BreadFormDto breadFormDto, BindingResult bindingResult, @RequestParam("imageFile") List<MultipartFile> imageFileList) {

        if(bindingResult.hasErrors()){
            return "bread/breadForm";
        }

        // 대표이미지 등록 안할 시, errorMessage 담기
//        if(itemImgFileList.get(0).isEmpty() && myStoreFormDto.getId() == null){
//            model.addAttribute("errorMessage", "대표이미지는 필수 입력 값 입니다.");
//            return "myStore/myStoreForm";
//        }

        try {
            breadService.saveBread(breadFormDto);  // imageFileList 이후에 추가
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "bread/breadForm";
        }

        return "redirect:/";
    }
}
