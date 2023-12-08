package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;
import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String nickname;
    private String password;

    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(user.getNickname(), user.getPassword());
    }

}
