package com.khu.bbangting.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NicknameUpdateDto {

    private String newNickname;

}
