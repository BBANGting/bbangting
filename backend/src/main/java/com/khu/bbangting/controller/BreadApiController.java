package com.khu.bbangting.controller;

import com.khu.bbangting.dto.BreadFormDto;
import com.khu.bbangting.service.BreadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("myStore/bread/new")
    public ResponseEntity<String> createBread(@Valid @RequestPart BreadFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors().toString());
        }

        // 대표이미지 등록 안할 시, errorMessage 담기
        if(imageFileList.get(0).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("대표이미지는 필수 입력 값입니다.");
        }

        try {
            breadService.saveBread(requestDto, imageFileList);
            return ResponseEntity.status(HttpStatus.CREATED).body("빵 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("빵 등록 중 에러가 발생하였습니다.");
        }

    }

    @DeleteMapping("myStore/bread/{breadId}")
    public ResponseEntity<String> deleteBread(@PathVariable Long breadId) {

        breadService.deleteBread(breadId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("빵 삭제 성공");
    }

    @GetMapping("myStore/bread/edit/{breadId}")
    public ResponseEntity<?> updateBreadPage(@PathVariable Long breadId, Model model) {

        try {
            BreadFormDto breadFormDto = breadService.getBreadForm(breadId);
            return ResponseEntity.ok(breadFormDto);
        } catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 제품을 찾을 수 없습니다.");
        }
    }

    @PostMapping("myStore/bread/edit/{breadId}")
    public ResponseEntity<?> updateBread(@PathVariable Long breadId, @Valid @RequestPart BreadFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("빵 정보 수정 실패 : " + bindingResult.getAllErrors().toString());
        }

        try {
            breadService.updateBread(breadId, requestDto, imageFileList);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("빵 정보 수정 성공");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("빵 수정 실패 : 이미 등록된 제품명입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("빵 수정 실패 : " + e.getMessage());
        }

    }
}
