package io.cafekiosk.spring.domain.order.entity;

import io.cafekiosk.spring.domain.orderproduct.entity.OrderProduct;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.util.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;


@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(List<Product> products,OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .collect(Collectors.toList());
    }


    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
        return Order.builder()
                .orderStatus(OrderStatus.INIT)
                .products(products)
                .registeredDateTime(registeredDateTime)
                .build();
    }

    private int calculateTotalPrice(List<Product> products) {

        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
