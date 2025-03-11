package io.cafekiosk.spring.api.controller;

import io.cafekiosk.spring.api.dto.ProductResponseDto;
import io.cafekiosk.spring.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/selling")
    public List<ProductResponseDto> getSellingProducts() {
        return productService.getSellingProducts();
    }

}
