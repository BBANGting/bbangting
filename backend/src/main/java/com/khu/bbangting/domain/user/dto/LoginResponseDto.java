package com.khu.bbangting.domain.user.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
@ToString
public class LoginResponseDto {

    private Long userId;
    private String username;

    @Builder
    public LoginResponseDto(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}
