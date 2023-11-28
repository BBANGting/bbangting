package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserUpdateDto { //회원 정보 불러오기

    private String email;
    private String username;
    private String nickname;
    private int banCount;

    @Builder
    public UserUpdateDto(String email, String username, String nickname, int banCount) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.banCount = banCount;
    }

    public static UserUpdateDto fromUser(User user) {
        return new UserUpdateDto(user.getEmail(), user.getUsername(), user.getNickname(), user.getBanCount());
    }

}
