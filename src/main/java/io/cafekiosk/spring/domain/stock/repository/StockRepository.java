package io.cafekiosk.spring.domain.stock.repository;

import io.cafekiosk.spring.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
