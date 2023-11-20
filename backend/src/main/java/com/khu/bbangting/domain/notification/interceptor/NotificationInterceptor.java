package com.khu.bbangting.domain.notification.interceptor;

import com.khu.bbangting.domain.notification.repository.NotificationRepository;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.model.UserAccount;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationRepository notificationRepository;

    public void postHandle(HttpServletRequest request, HttpServletRequest response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && !isRedirectView(modelAndView)
                && authentication != null && isTypeOfUserAccount(authentication)) {
            User user = ((UserAccount) authentication.getPrincipal()).getUser();

            long count = notificationRepository.countChecked(user, false);
            modelAndView.addObject("hasNotification", count > 0);
        }
    }

    private boolean isRedirectView(ModelAndView modelAndView) {
        Optional<ModelAndView> optionalModelAndView = Optional.ofNullable(modelAndView);
        return startsWithRedirect(optionalModelAndView) || isTypeOfRedirectView(optionalModelAndView);
    }

    private Boolean startsWithRedirect(Optional<ModelAndView> optionalModelAndView) {
        return optionalModelAndView.map(ModelAndView::getViewName)
                .map(viewName -> viewName.startsWith("redirect:"))
                .orElse(false);
    }

    private Boolean isTypeOfRedirectView(Optional<ModelAndView> optionalModelAndView) {
        return optionalModelAndView.map(ModelAndView::getView)
                .map(v -> v instanceof RedirectView)
                .orElse(false);
    }

    private boolean isTypeOfUserAccount(Authentication authentication) {
        return authentication.getPrincipal() instanceof
                UserAccount;
    }
}
