package com.khu.bbangting.error;

import com.khu.bbangting.security.exception.UserException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<?> handleIllegalArgumentException(Exception e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<?> handleIntegrityException(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 값입니다. errorMessage : " + e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleArgumentException(Exception e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, "errorMessage : " + e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<com.khu.bbangting.error.ErrorResponse> handleException(CustomException e) {
        log.error("Error occurs: {}", e.toString());

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new com.khu.bbangting.error.ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<com.khu.bbangting.error.ErrorResponse> applicationHandler(RuntimeException e) {
        log.error("Error occurs: {}", e.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new com.khu.bbangting.error.ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<com.khu.bbangting.error.ErrorResponse> validException(ConstraintViolationException e) {
        log.error("Error occurs: {}", e.toString());

        com.khu.bbangting.error.ErrorResponse errorResponse = com.khu.bbangting.error.ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("COMMON-ERR-400")
                .message("유효성 검사 실패 : " + e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
