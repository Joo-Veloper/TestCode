package io.cafekiosk.spring.api.order.service;

import io.cafekiosk.spring.IntegrationTestSupport;
import io.cafekiosk.spring.api.order.dto.request.OrderCreateRequestDto;
import io.cafekiosk.spring.api.order.dto.response.OrderResponseDto;
import io.cafekiosk.spring.api.order.service.dto.request.OrderCreateServiceRequestDto;
import io.cafekiosk.spring.domain.order.repository.OrderRepository;
import io.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import io.cafekiosk.spring.domain.stock.entity.Stock;
import io.cafekiosk.spring.domain.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.SELLING;
import static io.cafekiosk.spring.domain.product.entity.ProductType.*;
import static org.assertj.core.api.Assertions.*;

class OrderServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
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
        OrderResponseDto orderResponseDto = orderService.createOrder(requestDto.toServiceRequest(), registeredDateTime);

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
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateServiceRequestDto request = OrderCreateServiceRequestDto.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        // when
        OrderResponseDto orderResponseDto = orderService.createOrder(request, registeredDateTime);

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

    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트르 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 5000);
        Product product3 = createProduct(HANDMADE, "003", 10000);

        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequestDto requestDto = OrderCreateRequestDto.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // when
        OrderResponseDto orderResponseDto = orderService.createOrder(requestDto.toServiceRequest(), registeredDateTime);

        // then
        assertThat(orderResponseDto.getId()).isNotNull();
        assertThat(orderResponseDto)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 17000);
        assertThat(orderResponseDto.getProducts()).hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000),
                        tuple("002", 5000),
                        tuple("003", 10000)
                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 0),
                        tuple("002", 1)
                );
    }

    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithNoStock() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 5000);
        Product product3 = createProduct(HANDMADE, "003", 10000);

        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        stock1.deductQuantity(1); // todo
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequestDto requestDto = OrderCreateRequestDto.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(requestDto.toServiceRequest(), registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
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