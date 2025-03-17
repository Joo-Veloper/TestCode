package io.cafekiosk.spring.api.product.dto.request;

import io.cafekiosk.spring.domain.product.entity.ProductSellingStatus;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequestDto {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateRequestDto(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }
}
