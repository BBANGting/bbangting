package com.khu.bbangting.controller;

import com.khu.bbangting.dto.StoreFormDto;
import com.khu.bbangting.service.MyStoreService;
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
// 마이스토어 c,r,u,d api
public class MyStoreApiController {

    @Autowired
    private MyStoreService myStoreService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("myStore/new")
    public ResponseEntity<String> createMyStore(@Valid @RequestPart StoreFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList){

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors().toString());
        }

        // 스토어 로고 등록 안할 시, errorMessage
        if(imageFileList.get(0).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("스토어 로고는 필수 입력 값 입니다.");
        }

        try {
            myStoreService.saveStore(requestDto, imageFileList);
            return ResponseEntity.status(HttpStatus.CREATED).body("마이스토어 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("스토어 등록 중 에러가 발생하였습니다.");
        }

    }

    @DeleteMapping("myStore/{userId}")
    public ResponseEntity<String> deleteMyStore(@PathVariable Long userId) {

        myStoreService.deleteStore(userId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("마이스토어 삭제 성공");
    }

    @GetMapping("myStore/edit/{userId}")
    public ResponseEntity<?> updateMyStorePage(@PathVariable Long userId) {

        try {
            StoreFormDto storeFormDto = myStoreService.getStoreForm(userId);
            return ResponseEntity.ok(storeFormDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 제품을 찾을 수 없습니다.");
        }

    }

    @PostMapping("myStore/edit/{userId}")
    public ResponseEntity<?> updateMyStore(@PathVariable Long userId, @Valid @RequestPart StoreFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("스토어 수정 실패 : " + bindingResult.getAllErrors().toString());
        }

        try {
            myStoreService.updateStore(userId, requestDto, imageFileList);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("마이스토어 수정 성공");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("스토어 수정 실패 : 이미 존재하는 스토어 입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("스토어 수정 실패 : " + e.getMessage());
        }

    }
}
