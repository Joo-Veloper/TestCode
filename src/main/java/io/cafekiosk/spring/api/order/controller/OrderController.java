package io.cafekiosk.spring.api.order.controller;

import io.cafekiosk.spring.api.order.dto.OrderCreateRequestDto;
import io.cafekiosk.spring.api.order.dto.OrderResponseDto;
import io.cafekiosk.spring.api.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public OrderResponseDto createOrder(@RequestBody OrderCreateRequestDto requestDto) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return orderService.createOrder(requestDto, registeredDateTime);
    }

}
