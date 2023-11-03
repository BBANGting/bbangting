package com.khu.bbangting.controller.api;

import com.khu.bbangting.dto.ResponseDto;
import com.khu.bbangting.dto.UserFormDto;
import com.khu.bbangting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController //Json 형태로 객체 데이터 반환
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody UserFormDto userFormDto) {

        System.out.println("UserApiController: save 함수 호출됨");
        userService.saveUser(userFormDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody UserFormDto userFormDto) {
        userService.modiUser(userFormDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userFormDto.getUsername()
                , userFormDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
