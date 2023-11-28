package com.khu.bbangting.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto { //회원 수정

    @Length(min=8, message = "비밀번호는 8자 이상 입력하세요.")
    private String newPassword;

    @Length(min=8, message = "비밀번호는 8자 이상 입력하세요.")
    private String newPasswordConfirm;

}
