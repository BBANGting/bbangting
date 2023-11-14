package com.khu.bbangting.dto.user;

import com.khu.bbangting.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDto { //회원 가입

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

    public static UserFormDto fromUser(User user) {
        return new UserFormDto(user.getUsername(), user.getNickname(), user.getEmail(), user.getPassword());
    }

}
