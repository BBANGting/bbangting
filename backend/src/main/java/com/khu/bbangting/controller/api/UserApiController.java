package com.khu.bbangting.controller.api;

import com.khu.bbangting.dto.ResponseDto;
import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.model.User;
import com.khu.bbangting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController //Json 형태로 객체 데이터 반환
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> saveUser(UserFormDto userFormDto) {

        System.out.println("UserApiController: save 함수 호출됨");

        User user = userFormDto.toEntity();
        userService.saveUser(user);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
