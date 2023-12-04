//package com.khu.bbangting.domain.bread.controller;
//
//import com.khu.bbangting.domain.bread.model.Bread;
//import com.khu.bbangting.domain.bread.repository.BreadRepository;
//import com.khu.bbangting.domain.bread.service.BreadService;
//import com.khu.bbangting.error.CustomException;
//import com.khu.bbangting.error.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@RestController
//@RequiredArgsConstructor
//public class BreadSettingController {
//
//    private final BreadService breadService;
//    private final BreadRepository breadRepository;
//
//    @PostMapping("/bread/{breadId}/start")
//    public String startTing(@PathVariable Long breadId, RedirectAttributes attributes) {
//        Bread bread = breadRepository.findById(breadId)
//                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));
//
//        breadService.startTing(bread);
//        attributes.addFlashAttribute("message", "빵팅을 시작합니다.");
//
//        return "redirect:/";
//    }
//
//    @PostMapping("/bread/{breadId}/restart")
//    public String restartTing(@PathVariable Long breadId, RedirectAttributes attributes) {
//        Bread bread = breadRepository.findById(breadId)
//                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));
//
//        breadService.restartTing(bread);
//        attributes.addFlashAttribute("message", "빵팅을 재시작합니다.");
//
//        return "redirect:/";
//    }
//
//    @PostMapping("/bread/{breadId}/close")
//    public String closeTing(@PathVariable Long breadId, RedirectAttributes attributes) {
//        Bread bread = breadRepository.findById(breadId)
//                .orElseThrow(() -> new CustomException(ErrorCode.BREAD_NOT_FOUND));
//
//        breadService.closeTing(bread);
//        attributes.addFlashAttribute("message", "빵팅을 종료합니다.");
//
//        return "redirect:/";
//    }
//
//}
