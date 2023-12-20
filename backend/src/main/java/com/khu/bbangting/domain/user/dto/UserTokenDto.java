package com.khu.bbangting.domain.user.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    public UserTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
