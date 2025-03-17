package io.cafekiosk.spring.api.order.dto;

import io.cafekiosk.spring.api.product.dto.response.ProductResponseDto;
import io.cafekiosk.spring.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponseDto> products;

    @Builder
    public OrderResponseDto(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponseDto> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponseDto of(Order order) {

        return OrderResponseDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .products(order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponseDto.of(orderProduct.getProduct()))
                        .collect(Collectors.toList()))
                .build();

    }
}
