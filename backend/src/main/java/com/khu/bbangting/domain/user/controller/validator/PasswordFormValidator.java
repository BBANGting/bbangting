package com.khu.bbangting.domain.user.controller.validator;

import com.khu.bbangting.domain.user.dto.PasswordUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordUpdateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordUpdateDto passwordUpdateDto = (PasswordUpdateDto) target;

        if(!passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getNewPasswordConfirm())) {
            errors.rejectValue("newPassword", "wrong.value", "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }

}
