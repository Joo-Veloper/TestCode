package io.cafekiosk.spring.api.order.service;

import io.cafekiosk.spring.api.mail.service.MailService;
import io.cafekiosk.spring.domain.order.entity.Order;
import io.cafekiosk.spring.domain.order.entity.OrderStatus;
import io.cafekiosk.spring.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class OrderStaticService {
    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        // 해당 일자에 결재 완료된 주문들을 가져와서
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계산하고
        int totalAmount = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        // 메일 전송
        boolean result = mailService.sendMail(
                "no-reply@test.com",
                email,
                format("[매출통계] &s", orderDate),
                format("총 매출 합계는 %s원 입니다.", totalAmount)
        );

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }

        return true;
    }
}
