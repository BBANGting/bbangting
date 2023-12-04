package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String nickname;
    private String password;

    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(user.getId(), user.getNickname(), user.getPassword());
    }

}
