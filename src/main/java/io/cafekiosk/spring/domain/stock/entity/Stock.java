package io.cafekiosk.spring.domain.stock.entity;

import io.cafekiosk.spring.domain.util.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    @Builder
    public Stock(String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }


    public static Stock create(String productNumber, int quantity) {

        return Stock.builder()
                .productNumber(productNumber)
                .quantity(quantity)
                .build();
    }

    public boolean isQuantityLessThan(int quantity) {

        return this.quantity < quantity;
    }

    public void deductQuantity(int quantity) {
        if (isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
        }
        this.quantity -= quantity;
    }
}
