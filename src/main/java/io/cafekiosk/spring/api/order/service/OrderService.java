package io.cafekiosk.spring.api.order.service;

import io.cafekiosk.spring.api.order.dto.OrderCreateRequestDto;
import io.cafekiosk.spring.api.order.dto.OrderResponseDto;
import io.cafekiosk.spring.domain.order.entity.Order;
import io.cafekiosk.spring.domain.order.repository.OrderRepository;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import io.cafekiosk.spring.domain.stock.entity.Stock;
import io.cafekiosk.spring.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto, LocalDateTime registeredDateTime) {
        List<String> productNumbers = requestDto.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        deductStuckQuantities(products);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.of(savedOrder);
    }

    private void deductStuckQuantities(List<Product> products) {
        List<String> stockProductNumbers = extractStockProductNumbers(products);
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
        Map<String, Long> productCountingMap = CreateCountingMapBy(stockProductNumbers);

        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }

    private List<String> extractStockProductNumbers(List<Product> products) {

        return products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .toList();
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);

        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private Map<String, Long> CreateCountingMapBy(List<String> stockProductNumbers) {
        
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }
}
