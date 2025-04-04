package io.cafekiosk.spring.api.product.controller;

import io.cafekiosk.spring.api.product.dto.request.ProductCreateRequestDto;
import io.cafekiosk.spring.api.product.dto.response.ProductResponseDto;
import io.cafekiosk.spring.api.product.service.ProductService;
import io.cafekiosk.spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ApiResponse<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateRequestDto requestDto) {

        return ApiResponse.ok(productService.createProduct(requestDto.toServiceRequest()));
    }

    @GetMapping("/selling")
    public ApiResponse<List<ProductResponseDto>> getSellingProducts() {

        return ApiResponse.ok(productService.getSellingProducts());
    }

}
