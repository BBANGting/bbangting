package com.khu.bbangting.domain.user.controller;

import com.khu.bbangting.domain.user.controller.validator.NicknameFormValidator;
import com.khu.bbangting.domain.user.controller.validator.PasswordFormValidator;
import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserSettingController {

    private final UserService userService;
    private final PasswordFormValidator passwordFormValidator;
    private final NicknameFormValidator nicknameFormValidator;

    @InitBinder("passwordUpdateDto")
    public void passwordFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(passwordFormValidator);
    }

    @InitBinder("nicknameUpdateDto")
    public void nicknameFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(nicknameFormValidator);
    }

    /**
     * 비밀번호 수정 페이지
     */
    @GetMapping("/setting/password")
    public String passwordUpdateForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        model.addAttribute(new PasswordUpdateDto());

        return "setting/password";
    }

    /**
     * 비밀번호 수정
     */
    @PostMapping("/setting/password")
    public String updatePassword(@AuthenticationPrincipal User user, @Valid PasswordUpdateDto passwordUpdateDto, Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(user);
            return "setting/password";
        }

        userService.updatePassword(user, passwordUpdateDto.getNewPassword());
        attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");

        return "redirect:/myPage";
    }

    /**
     * 닉네임 수정 페이지
     */
    @GetMapping("setting/nickname")
    public String nicknameUpdateForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        model.addAttribute(new NicknameUpdateDto(user.getNickname()));
        return "/setting/nickname";
    }

    /**
     * 닉네임 수정
     */
    @PostMapping("setting/nickname")
    public String updateNickname(@AuthenticationPrincipal User user, @Valid NicknameUpdateDto nicknameUpdateDto, Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(user);
            return "/setting/nickname";
        }
        userService.updateNickname(user, nicknameUpdateDto.getNickname());
        attributes.addFlashAttribute("message", "닉네임을 수정하였습니다.");
        return "redirect:/myPage";
    }

}
