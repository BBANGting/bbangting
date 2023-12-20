package com.khu.bbangting.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // UserDetailsService에서 Authorities를 가져와 확인할 때,
    // 자동으로 ROLE_ 붙여 확인 가능
    // hasRole("USER") == hasAuthority("ROLE_USER")
    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
