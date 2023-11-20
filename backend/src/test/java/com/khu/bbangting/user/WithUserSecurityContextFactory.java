package com.khu.bbangting.user;

import com.khu.bbangting.config.auth.PrincipalDetailService;
import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithUserSecurityContextFactory implements WithSecurityContextFactory<WithUser> {

    private final UserService userService;
    private PrincipalDetailService principalDetailService;

    public WithUserSecurityContextFactory(UserService userService, PrincipalDetailService principalDetailService) {
        this.userService = userService;
        this.principalDetailService = principalDetailService;
    }

    @Override
    public SecurityContext createSecurityContext(WithUser annotation) {
        String[] emails = annotation.value();
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        for (String email : emails) {
            UserJoinFormDto userJoinFormDto = new UserJoinFormDto();
            userJoinFormDto.setUsername("hyeyeon");
            userJoinFormDto.setNickname("hyeyeon");
            userJoinFormDto.setEmail(email);
            userJoinFormDto.setPassword("1234asdf!");
            userService.joinUser(userJoinFormDto);

            UserDetails principal = principalDetailService.loadUserByUsername(email);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
            context.setAuthentication(authentication);
        }

        return context;
    }
}
