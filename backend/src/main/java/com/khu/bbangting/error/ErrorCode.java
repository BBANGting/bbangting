package com.khu.bbangting.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_LOGIN_EMAIL(HttpStatus.CONFLICT,"MEMBER-ERR-409", "loginEmail is duplicated"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER-ERR-401", "Password is invalid"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "MEMBER-ERR-401", "Permission is invalid"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER-ERR-404", "user not founded"),
    BREAD_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-ERR-404", "product not founded"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER-ERR-404", "order not founded"),
    TING_NOT_FOUND(HttpStatus.NOT_FOUND, "TING-ERR-404", "ting not founded"),

    BREAD_SOLD_OUT(HttpStatus.NOT_FOUND, "PRODUCT-ERR-404", "product quantity is 0"),
    NOT_BBANGTING_TIME(HttpStatus.METHOD_NOT_ALLOWED, "ORDER-ERR-405", "not sale time"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "Internal server error");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
