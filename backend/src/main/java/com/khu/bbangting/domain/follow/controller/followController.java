package com.khu.bbangting.domain.follow.controller;

import com.khu.bbangting.domain.follow.dto.FollowDto;
import com.khu.bbangting.domain.follow.model.Follow;
import com.khu.bbangting.domain.follow.repository.FollowRepository;
import com.khu.bbangting.domain.follow.service.FollowService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class followController {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;

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

    // user의 해당 store 팔로우 여부 체크
    @GetMapping("/check/store/{storeId}")
    public ResponseEntity<String> checkFollow(Authentication authentication, @PathVariable Long storeId) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Follow> follow = followRepository.findByStoreIdAndUserId(storeId, user.getId());

        if (follow.isEmpty()) {
            return ResponseEntity.ok().body("언팔로우 상태입니다.");
        } else {
            return ResponseEntity.ok().body("팔로우 상태입니다.");
        }

    }
}
