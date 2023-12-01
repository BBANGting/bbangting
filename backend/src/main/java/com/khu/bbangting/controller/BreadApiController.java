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
    List<MultipartFile> imageFileList) throws Exception {

        // 유효성 검사
        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("빵 등록 실패 : " + bindingResult.getFieldError().getDefaultMessage());
        }

        breadService.saveBread(requestDto, imageFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body("빵 등록 성공");

    }

    @DeleteMapping("myStore/bread/{breadId}")
    public ResponseEntity<String> deleteBread(@PathVariable Long breadId) {

        breadService.deleteBread(breadId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("빵 삭제 성공");
    }

    @GetMapping("myStore/bread/edit/{breadId}")
    public ResponseEntity<?> updateBreadPage(@PathVariable Long breadId) {

        BreadFormDto breadFormDto = breadService.getBreadForm(breadId);
        return ResponseEntity.ok(breadFormDto);

    }

    @PostMapping("myStore/bread/edit/{breadId}")
    public ResponseEntity<?> updateBread(@PathVariable Long breadId, @Valid @RequestPart BreadFormDto requestDto, BindingResult bindingResult, @RequestPart("imageFile")
    List<MultipartFile> imageFileList) throws Exception {

        if (bindingResult.hasErrors()) {
            log.info("requestDto 검증 오류 발생 errors={}", bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("빵 정보 수정 실패 : " + bindingResult.getFieldError().getDefaultMessage());
        }

        breadService.updateBread(breadId, requestDto, imageFileList);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("빵 정보 수정 성공");

    }
}
