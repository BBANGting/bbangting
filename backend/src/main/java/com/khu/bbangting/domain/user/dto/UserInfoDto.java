package com.khu.bbangting.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class UserInfoDto {

    private String email;
    private String username;
    private String nickname;
    private int banCount;

    @Builder
    public UserInfoDto(String email, String username, String nickname, int banCount) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.banCount = banCount;
    }

}
