package io.cafekiosk.spring.api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequestDto {

    private List<String> productNumbers;

    @Builder
    private OrderCreateServiceRequestDto(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
