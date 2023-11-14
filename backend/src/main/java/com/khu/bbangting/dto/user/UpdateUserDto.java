package com.khu.bbangting.dto.user;

import com.khu.bbangting.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto { //회원 수정

    private String nickname;

    private String password;

    public User toEntity() {
        return User.builder()
                .username(nickname)
                .password(password)
                .build();
    }
}
