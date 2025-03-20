package io.cafekiosk.spring.domain.order.repository;

import io.cafekiosk.spring.domain.order.entity.Order;
import io.cafekiosk.spring.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.registeredDateTime >= :startDateTime " +
            "and o.registeredDateTime < :endDateTime " +
            "and o.orderStatus = :orderStatus")
    List<Order> findOrdersBy(@Param("startDateTime") LocalDateTime startDateTime,
                             @Param("endDateTime") LocalDateTime endDateTime,
                             @Param("orderStatus") OrderStatus orderStatus);

}
