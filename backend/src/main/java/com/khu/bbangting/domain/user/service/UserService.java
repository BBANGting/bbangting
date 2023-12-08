package com.khu.bbangting.domain.user.service;

import com.khu.bbangting.domain.user.dto.*;
import com.khu.bbangting.domain.user.model.User;
import com.khu.bbangting.domain.user.model.UserDetailsImpl;
import com.khu.bbangting.domain.user.repository.UserRepository;
import com.khu.bbangting.jwt.TokenProvider;
import com.khu.bbangting.jwt.dto.TokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입
    @Transactional
    public ResponseDto<?> joinUser(JoinRequestDto requestDto) {

        if (isPresentUser(requestDto.getEmail()) != null) {
            return ResponseDto.fail("DUPLICATED_EMAIL", "중복된 이메일 입니다.");
        }

        if (isPresentUser(requestDto.getNickname()) != null) {
            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임 입니다.");
        }

        saveNewUser(requestDto);

        return ResponseDto.success("회원가입에 성공했습니다.");
    }

    private User saveNewUser(JoinRequestDto requestDto) {

        String rawPassword = requestDto.getPassword(); // encoding 전 비밀번호
        String encPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encPassword)
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .build();

        return userRepository.save(user);
    }

    // 로그인
    @Transactional
    public ResponseDto<?> loginUser(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = isPresentUser(requestDto.getEmail());

        if (user == null) {
            return ResponseDto.fail("USER_NOT_FOUND", "사용자를 찾을 수 없습니다.");
        }

        if (!user.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_PASSWORD", "비밀번호가 틀렸습니다.");
        }

        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // 사용자 인증
        SecurityContextHolder.getContext().setAuthentication(authentication); //security가 인증한 사용자로 등록

        UserDetailsImpl userDetails1 = (UserDetailsImpl) authentication.getPrincipal(); // UserDetails를 구현한 현재 사용자 정보 가져오기
        TokenDto tokenDto = tokenProvider.generateTokenDto(userDetails1);

        response.addHeader("Authorization", "BEARER" + " " + tokenDto.getAccessToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());

        // 로그인 했을 때 팔로우한 빵집 중 그날 진행하는 빵팅 알려주기 (시간, 빵 정보 포함)

        return ResponseDto.success(
                UserResponseDto.builder()
                        .nickname(user.getNickname())
                        .build());
    }

    // 로그아웃
    public ResponseDto<?> logout(UserDetailsImpl userDetails) {
        System.out.println("로그아웃 시도");
        if (userDetails.getAuthorities() == null) {
            ResponseDto.fail("USER_NOT_FOUND", "사용자를 찾을 수 없습니다");
        }
        return tokenProvider.deleteRefreshToken(userDetails.getUser());
    }

    @Transactional(readOnly = true)
    public User isPresentUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

}