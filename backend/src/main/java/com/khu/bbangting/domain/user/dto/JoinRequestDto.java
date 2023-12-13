package com.khu.bbangting.domain.user.dto;

import com.khu.bbangting.domain.user.model.User;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequestDto { //회원 가입

    @NotBlank(message="이름은 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*${2,10}", message = "닉네임 2~10자로 입력하셔야 합니다(특수문자 불가).")
    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message="이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message="비밀번호는 필수 입력 값입니다.")
    @Size(min=8, max = 18, message = "비밀번호는 8자 이상 입력하세요.")
    private String password;

    @Builder
    public JoinRequestDto(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static User ofEntity(JoinRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

}
