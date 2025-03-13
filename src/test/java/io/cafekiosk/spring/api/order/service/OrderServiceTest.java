package io.cafekiosk.spring.api.order.service;

import io.cafekiosk.spring.api.order.dto.OrderCreateRequestDto;
import io.cafekiosk.spring.api.order.dto.OrderResponseDto;
import io.cafekiosk.spring.domain.order.repository.OrderRepository;
import io.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.*;
import static io.cafekiosk.spring.domain.product.entity.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
    }

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 5000);
        Product product3 = createProduct(HANDMADE, "003", 10000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequestDto requestDto = OrderCreateRequestDto.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        // when
        OrderResponseDto orderResponseDto = orderService.createOrder(requestDto, registeredDateTime);

        // then
        assertThat(orderResponseDto.getId()).isNotNull();
        assertThat(orderResponseDto)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 6000);
        assertThat(orderResponseDto.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 5000)
                );
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 5000);
        Product product3 = createProduct(HANDMADE, "003", 10000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequestDto requestDto = OrderCreateRequestDto.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        // when
        OrderResponseDto orderResponseDto = orderService.createOrder(requestDto, registeredDateTime);

        // then
        assertThat(orderResponseDto.getId()).isNotNull();
        assertThat(orderResponseDto)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 2000);
        assertThat(orderResponseDto.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000)
                );
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}