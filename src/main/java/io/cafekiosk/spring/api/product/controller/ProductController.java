package io.cafekiosk.spring.api.product.controller;

import io.cafekiosk.spring.api.product.dto.ProductResponseDto;
import io.cafekiosk.spring.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/selling")
    public List<ProductResponseDto> getSellingProducts() {
        return productService.getSellingProducts();
    }

}
