package com.khu.bbangting.domain.notification.controller;

import com.khu.bbangting.domain.notification.model.Notification;
import com.khu.bbangting.domain.notification.repository.NotificationRepository;
import com.khu.bbangting.domain.notification.service.NotificationService;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @GetMapping("/noti/{userId}")
    public String getNotifications(@PathVariable Long userId, Model model) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        long countChecked = notificationRepository.countByUserAndChecked(user, false);
        List<Notification> notifications = notificationRepository.findAll();
        putCategorizeNotification(model, notifications, countChecked);

        model.addAttribute("isNew", true);
        notificationService.markAsRead(notifications);

        return "noti/list";
    }

    private void putCategorizeNotification(Model model, List<Notification> notifications, long countChecked) {


        ArrayList<Notification> tingNotifications = new ArrayList<>();

        model.addAttribute("countChecked", countChecked);
        model.addAttribute("notifications", notifications);
        model.addAttribute("tingNotifications", tingNotifications);
    }

}
