package io.cafekiosk.spring.api.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private List<String> productNumbers;

    @Builder
    private OrderCreateRequestDto(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
