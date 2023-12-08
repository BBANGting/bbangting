package com.khu.bbangting.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class PasswordUpdateDto {

    private String newPassword;

    private String newPasswordConfirm;

    @Builder
    public PasswordUpdateDto(String newPassword, String newPasswordConfirm) {
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

}
