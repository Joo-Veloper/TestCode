package io.cafekiosk.spring.api.order.service;

import io.cafekiosk.spring.api.order.dto.OrderCreateRequestDto;
import io.cafekiosk.spring.api.order.dto.OrderResponseDto;
import io.cafekiosk.spring.domain.order.entity.Order;
import io.cafekiosk.spring.domain.order.repository.OrderRepository;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto, LocalDateTime registeredDateTime) {
        List<String> productNumbers = requestDto.getProductNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.of(savedOrder);
    }
}
