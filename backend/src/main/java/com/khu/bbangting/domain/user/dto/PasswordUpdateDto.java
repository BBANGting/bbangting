package com.khu.bbangting.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class PasswordUpdateDto {

    @Length(min=8, message = "비밀번호는 8자 이상 입력하세요.")
    private String newPassword;

    @Length(min=8, message = "비밀번호는 8자 이상 입력하세요.")
    private String newPasswordConfirm;

}
