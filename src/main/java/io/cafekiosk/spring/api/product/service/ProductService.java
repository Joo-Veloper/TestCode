package io.cafekiosk.spring.api.product.service;

import io.cafekiosk.spring.api.product.dto.response.ProductResponseDto;
import io.cafekiosk.spring.api.product.service.dto.request.ProductCreateServiceRequestDto;
import io.cafekiosk.spring.domain.product.entity.Product;
import io.cafekiosk.spring.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.cafekiosk.spring.domain.product.entity.ProductSellingStatus.forDisplay;

/**
 * readOnly = true : 읽기 전용
 * CRUD 에서 CUD 동작 X / Only Read
 * JPA : CUD 스냅샷 저장, 변경감지 안해도 되는 이점 생김( 성능 향상 )
 * <p>
 * CQRS : Command 와 query 를 분리하자 ( 책임을 분리해서 서로 연관이 없게 하자 )
 *
 * @Transactional(readOnly = true) 전체 달고 메서드별 CUD에 @Transaction을 다시 달아주는방법 추천
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    @Transactional
    public ProductResponseDto createProduct(ProductCreateServiceRequestDto requestDto) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = requestDto.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.of(savedProduct);
    }

    public List<ProductResponseDto> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());

        return products.stream()
                .map(ProductResponseDto::of)
                .collect(Collectors.toList());
    }
}
