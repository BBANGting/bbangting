package com.khu.bbangting.domain.user.dto;

import lombok.Builder;

public class UserResponseDto { //회원 정보 불러오기

    private Long userId;
    private String email;
    private String username;
    private String nickname;
    private int banCount;

    @Builder
    public UserResponseDto(Long userId, String email, String username, String nickname, int banCount) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.banCount = banCount;
    }
}
