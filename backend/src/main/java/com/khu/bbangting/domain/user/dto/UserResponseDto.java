package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;

public class UserResponseDto { //회원 정보 불러오기

    private String email;
    private String username;
    private String nickname;
    private int banCount;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.banCount = user.getBanCount();
    }
}
