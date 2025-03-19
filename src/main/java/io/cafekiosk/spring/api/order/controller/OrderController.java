package io.cafekiosk.spring.api.order.controller;

import io.cafekiosk.spring.api.order.dto.request.OrderCreateRequestDto;
import io.cafekiosk.spring.api.order.dto.response.OrderResponseDto;
import io.cafekiosk.spring.api.order.service.OrderService;
import io.cafekiosk.spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ApiResponse<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateRequestDto requestDto) {
        LocalDateTime registeredDateTime = LocalDateTime.now();

        return ApiResponse.ok(orderService.createOrder(requestDto.toServiceRequest() , registeredDateTime));
    }

}
