package com.khu.bbangting.domain.notification.controller;

import com.khu.bbangting.domain.notification.model.Notification;
import com.khu.bbangting.domain.notification.repository.NotificationRepository;
import com.khu.bbangting.domain.notification.service.NotificationService;
import com.khu.bbangting.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public String getNotifications(@AuthenticationPrincipal User user, Model model) {

        List<Notification> notifications = notificationRepository.findAll();
        putCategorizeNotification(model, notifications);

        model.addAttribute("isNew", true);
        notificationService.markAsRead(notifications);

        return "notification";
    }

    private void putCategorizeNotification(Model model, List<Notification> notifications) {

        ArrayList<Notification> tingTimeNotifications = new ArrayList<>();
        ArrayList<Notification> tingCreatedNotifications = new ArrayList<>();
        ArrayList<Notification> tingUpdatedNotifications = new ArrayList<>();

        for (Notification notification: notifications) {
            switch (notification.getNotificationType()) {
                case TING_CREATED: {
                    tingCreatedNotifications.add(notification);
                    break;
                }
                case TING_UPDATED: {
                    tingUpdatedNotifications.add(notification);
                    break;
                }
            }
        }

        model.addAttribute("notifications", notifications);
        model.addAttribute("tingCreatedNotifications", tingCreatedNotifications);
        model.addAttribute("tingUpdatedNotifications", tingUpdatedNotifications);
    }

}
