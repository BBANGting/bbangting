package com.khu.bbangting.domain.bread.event;

import com.khu.bbangting.domain.bread.model.Bread;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class TingValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Bread.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Bread bread = (Bread) target;

        if (isEarlierThanNow(bread.getTingDateTime())) {
            errors.rejectValue("tingDateTime", "wrong.datetime", "빵팅 시작 일시를 정확히 입력하세요.");
        }
    }

    private boolean isEarlierThanNow(LocalDateTime time) {
        return time.isBefore(LocalDateTime.now());
    }

}
