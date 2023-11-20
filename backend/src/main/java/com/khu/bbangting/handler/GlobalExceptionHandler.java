package com.khu.bbangting.handler;

import com.khu.bbangting.domain.user.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception exception) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception.getMessage());
    }
}
