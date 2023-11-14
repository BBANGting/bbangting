package com.khu.bbangting.controller;

import com.khu.bbangting.dto.user.UserResponseDto;
import com.khu.bbangting.model.User;
import com.khu.bbangting.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 1. 마이페이지 호출
    @GetMapping("/myPage/{userId}")
    public String myPageForm(Model model, @AuthenticationPrincipal User userInfo) throws Exception {

        UserResponseDto userResponseDto = new UserResponseDto(userInfo);

        // Model: 뷰로 넘겨주기, @AuthenticationPrincipal: 로그인한 사용자 정보 얻어오기
        model.addAttribute("userInfo", userInfo);

        return "/myPageForm";
    }

    // 2. 회원정보 수정

    // 3. 구매 내역

    // 4. 결제수단 -> 보류

    // 5. 리뷰관리 -> 보류

    // 6. 팔로잉
}
