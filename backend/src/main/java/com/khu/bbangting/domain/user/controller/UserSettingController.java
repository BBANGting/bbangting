package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.service.UserService;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final UserRepository userRepository;
    private final UserService userService;

    // 비밀번호 수정하기
    @PostMapping("/myPage/{userId}/rePassword")
    public String updatePassword(@PathVariable Long userId, @RequestBody PasswordUpdateDto passwordUpdateDto, Errors errors, Model model, RedirectAttributes attributes) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (errors.hasErrors()) {
            model.addAttribute(user);
            return "setting/password";
        }

        userService.updatePassword(user, passwordUpdateDto.getNewPassword());
        attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");

        return "redirect:/myPage";
    }

    // 닉네임 수정하기
    @PostMapping("/myPage/{userId}/reNickname")
    public String updateNickname(@PathVariable Long userId, @RequestBody NicknameUpdateDto nicknameUpdateDto, Errors errors, Model model, RedirectAttributes attributes) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (errors.hasErrors()) {
            model.addAttribute(user);
            return "setting/nickname";
        }

        userService.updateNickname(user, nicknameUpdateDto.getNewNickname());
        attributes.addFlashAttribute("message", "닉네임을 수정하였습니다.");

        return "redirect:/myPage";
    }

}
