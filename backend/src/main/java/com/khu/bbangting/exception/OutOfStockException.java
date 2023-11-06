package com.khu.bbangting.exception;

//상품의 주문 수량보다 재고의 수가 적을 때 exception 발생
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }
}
