package com.khu.bbangting.config.jwt;

import com.khu.bbangting.error.CustomException;
import com.khu.bbangting.error.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return authentication.getName();
    }
}
