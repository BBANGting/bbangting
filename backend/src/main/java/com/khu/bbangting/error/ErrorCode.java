package com.khu.bbangting.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_LOGIN_EMAIL(HttpStatus.CONFLICT,"USER-ERR-409", "loginEmail is duplicated"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER-ERR-401", "Password is invalid"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "USER-ERR-401", "Permission is invalid"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER-ERR-404", "user not founded"),
    BREAD_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-ERR-404", "product not founded"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER-ERR-404", "order not founded"),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE-ERR-404", "store not founded"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW-ERR-404", "review not founded"),
    TING_NOT_FOUND(HttpStatus.NOT_FOUND, "TING-ERR-404", "ting not founded"),
    NOTI_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION-ERR-404", "notification not founded"),


    BREAD_SOLD_OUT(HttpStatus.NOT_FOUND, "PRODUCT-ERR-404", "product quantity is 0"),
    NOT_BBANGTING_TIME(HttpStatus.METHOD_NOT_ALLOWED, "ORDER-ERR-405", "not sale time"),
    BAN_USER(HttpStatus.METHOD_NOT_ALLOWED, "USER-ERR-405", "user is banned"),

    ORDER_IS_EXIST(HttpStatus.CONFLICT, "ORDER_ERR_409", "order is already exist"),
    REVIEW_IS_EXIST(HttpStatus.CONFLICT, "REVIEW_ERR_409", "review is already exist"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "Internal server error");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
