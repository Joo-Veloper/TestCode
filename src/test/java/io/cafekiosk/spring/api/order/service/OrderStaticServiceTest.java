package io.cafekiosk.spring.api.order.service;

import io.cafekiosk.spring.client.mail.MailSendClient;
import io.cafekiosk.spring.domain.hisotry.entity.mail.MailSendHistory;
import io.cafekiosk.spring.domain.hisotry.repository.MailSendHistoryRepository;
import io.cafekiosk.spring.domain.order.entity.Order;
import io.cafekiosk.spring.domain.order.entity.OrderStatus;
import io.cafekiosk.spring.domain.order.repository.OrderRepository;
import io.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.SELLING;
import static io.cafekiosk.spring.domain.product.entity.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderStaticServiceTest {

    @Autowired
    private OrderStaticService orderStaticService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제 완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2025, 3, 21, 0, 0);

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(products, LocalDateTime.of(2025,3,21,23,59,59));
        Order order2 = createPaymentCompletedOrder(products, now);
        Order order3 = createPaymentCompletedOrder(products, LocalDateTime.of(2025,3,22,23,59,59));
        Order order4 = createPaymentCompletedOrder(products, LocalDateTime.of(2025, 3, 23, 0, 0));

        // stubbing
        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        // when
        boolean result = orderStaticService.sendOrderStatisticsMail(LocalDate.of(2025, 3, 21), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원 입니다.");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        Order order1 = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();

        return orderRepository.save(order1);
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