package com.khu.bbangting.domain.user.controller.validator;

import com.khu.bbangting.domain.user.dto.NicknameUpdateDto;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NicknameFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NicknameUpdateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NicknameUpdateDto nicknameForm = (NicknameUpdateDto) target;
        User user = userRepository.findByNickname(nicknameForm.getNickname());
        if (user != null) {
            errors.rejectValue("nickname", "wrong.value", "이미 사용중인 닉네임입니다.");
        }
    }

}
