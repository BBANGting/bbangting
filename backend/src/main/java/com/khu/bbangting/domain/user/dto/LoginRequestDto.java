package com.khu.bbangting.domain.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
