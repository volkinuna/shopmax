package com.shopmax.exception;

public class OutOfStockException extends RuntimeException {

    //재고 부족 예외(상품 주문 수량보다 재고가 적을 경우 발생하는 Exception)
    public OutOfStockException(String message) {
        super(message);
    }
}
