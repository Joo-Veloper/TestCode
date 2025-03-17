package io.cafekiosk.spring.api.product.service;

import io.cafekiosk.spring.api.product.dto.request.ProductCreateRequestDto;
import io.cafekiosk.spring.api.product.dto.response.ProductResponseDto;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.forDisplay;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
        String nextProductNumber = createNextProductNumber();

        return ProductResponseDto.builder()
                .productNumber(nextProductNumber)
                .type(requestDto.getType())
                .sellingStatus(requestDto.getSellingStatus())
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .build();
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // %03d = 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);
    }


    public List<ProductResponseDto> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());

        return products.stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }
}
