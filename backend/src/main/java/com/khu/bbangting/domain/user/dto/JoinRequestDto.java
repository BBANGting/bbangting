package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequestDto { //회원 가입

    private String username;
    private String nickname;
    private String email;
    private String password;

    @Builder
    public JoinRequestDto(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static User ofEntity(JoinRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

}
