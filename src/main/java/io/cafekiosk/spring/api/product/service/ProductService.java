package io.cafekiosk.spring.api.product.service;

import io.cafekiosk.spring.api.product.dto.request.ProductCreateRequestDto;
import io.cafekiosk.spring.api.product.dto.response.ProductResponseDto;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.entity.ProductSellingStatus;
import io.cafekiosk.spring.domain.product.entity.ProductType;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.*;
import static io.cafekiosk.spring.domain.product.entity.ProductType.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
        String latestProductNumber = productRepository.findLatestProductNumber();

        return ProductResponseDto.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();
    }


    public List<ProductResponseDto> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());

        return products.stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }
}
