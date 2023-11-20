package com.khu.bbangting.domain.user.controller.validator;

import com.khu.bbangting.domain.user.dto.UserJoinFormDto;
import com.khu.bbangting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserJoinFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserJoinFormDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserJoinFormDto userJoinFormDto = (UserJoinFormDto) target;
        if (userRepository.existsByEmail(userJoinFormDto.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{userJoinFormDto.getEmail()},
                    "이미 사용중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(userJoinFormDto.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{userJoinFormDto.getEmail()},
                    "이미 사용중인 닉네임입니다.");
        }
    }
}
