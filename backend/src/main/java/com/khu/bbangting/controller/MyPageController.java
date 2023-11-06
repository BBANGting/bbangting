package com.khu.bbangting.controller;

import com.khu.bbangting.config.auth.PrincipalDetail;
import com.khu.bbangting.model.Order;
import com.khu.bbangting.model.User;
import com.khu.bbangting.service.MyPageService;
import com.khu.bbangting.service.OrderService;
import com.khu.bbangting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;
    private final MyPageService myPageService;
    private final OrderService orderService;

    //마이페이지
    @GetMapping("/user/{userId}")
    public String userPage(@PathVariable("id") Long userId, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        if(principalDetail.getUser().getId() == userId) {
            model.addAttribute("user", myPageService.findUser(userId));
            return "/user/mypageForm";
        } else {
            return "/user/loginForm";
        }
    }

    //유저 정보 수정 페이지
    @GetMapping("/user/modify/{userId}")
    public String modiUser(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("user", myPageService.findUser(userId));

        return "/user/modifyForm";
    }

    //유저 정보 수정
    @PostMapping("/user/update/{userId}")
    public String updateUser(@PathVariable("id") Long userId, User user) {
        User tempUser = myPageService.findUser(userId);
        tempUser.setEmail(user.getEmail());
        tempUser.setPassword(user.getPassword());
        tempUser.setUsername(user.getUsername());
        tempUser.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));

        userService.updateUser(tempUser);

        return "redirect:/user/mypageForm";
    }

    //주문내역 조회 페이지



    //주문 취소
    @PostMapping("/user/{userId}/order/{orderId}/cancel")
    public ResponseEntity<Long> cancelOrder(@PathVariable("orderId") Long orderId) {

        return new ResponseEntity<Long>(orderService.cancelOrder(orderId), HttpStatus.ACCEPTED);
    }

}
