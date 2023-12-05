package com.khu.bbangting.domain.follow.controller;

import com.khu.bbangting.domain.follow.dto.FollowDto;
import com.khu.bbangting.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class followController {

    @Autowired
    private FollowService followService;

    // 스토어 상세 페이지 > 팔로우 기능
    @PostMapping("/store/follow")
    public ResponseEntity<String> followStore(@RequestBody FollowDto followDto) {

        try {
            String message = followService.follows(followDto);
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("스토어 팔로우를 실패하였습니다. errorMessage : " + e.getMessage());
        }
    }
}
