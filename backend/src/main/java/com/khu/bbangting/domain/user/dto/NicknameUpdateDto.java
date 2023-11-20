package com.khu.bbangting.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameUpdateDto {

    @NotBlank
    @Length(max = 10)
    private String nickname;

    public NicknameUpdateDto(String nickname) {
        this.nickname = nickname;
    }

}
