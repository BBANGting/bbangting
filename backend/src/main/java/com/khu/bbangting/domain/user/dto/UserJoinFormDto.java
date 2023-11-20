package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
public class UserJoinFormDto { //회원 가입

    @NotBlank(message="이름은 필수 입력 값입니다.")
    private String username;

    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message="이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message="비밀번호는 필수 입력 값입니다.")
    @Length(min=8, message = "비밀번호는 8자 이상 입력하세요.")
    private String password;

    public User toEntity() {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }

}
