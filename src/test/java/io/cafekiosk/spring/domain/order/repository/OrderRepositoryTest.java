package io.cafekiosk.spring.domain.order.repository;

import io.cafekiosk.spring.domain.order.entity.Order;
import io.cafekiosk.spring.domain.order.entity.OrderStatus;
import io.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.SELLING;
import static io.cafekiosk.spring.domain.product.entity.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("결제 완료된 주문을 조회한다.")
    @Test
    void findOrdersByCompletedStatus() {
        // Given
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endDateTime = LocalDateTime.now();

        Product product = createProduct("001", 1500);
        productRepository.save(product);

        Order completedOrder1 = createOrder(OrderStatus.COMPLETED, startDateTime.plusHours(2), product);
        Order completedOrder2 = createOrder(OrderStatus.COMPLETED, startDateTime.plusHours(5), product);
        Order canceledOrder = createOrder(OrderStatus.CANCELED, startDateTime.plusHours(3), product);
        orderRepository.saveAll(List.of(completedOrder1, completedOrder2, canceledOrder));

        // When
        List<Order> completedOrders = orderRepository.findOrdersBy(startDateTime, endDateTime, OrderStatus.COMPLETED);

        // Then
        assertThat(completedOrders).hasSize(2)
                .extracting(Order::getOrderStatus)
                .containsOnly(OrderStatus.COMPLETED);
    }

    private Product createProduct(String productNumber, int price) {

        return Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("테스트 메뉴")
                .build();
    }

    private Order createOrder(OrderStatus status, LocalDateTime registeredDateTime, Product product) {

        return Order.builder()
                .orderStatus(status)
                .registeredDateTime(registeredDateTime)
                .products(List.of(product))
                .build();
    }
}