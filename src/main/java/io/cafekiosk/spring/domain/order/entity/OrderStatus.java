package io.cafekiosk.spring.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("주문생성"),
    CANCELED("주문취소"),
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_FAIL("결제실패"),
    RECEOVED("주문접수"),
    COMPLETED("처리완료");

    private final String text;
}
