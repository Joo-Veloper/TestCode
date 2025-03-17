package io.cafekiosk.spring.api.product.dto.request;

import io.cafekiosk.spring.domain.product.entity.ProductSellingStatus;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import lombok.Getter;

@Getter
public class ProductCreateRequestDto {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

}
