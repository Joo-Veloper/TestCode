package io.cafekiosk.spring.domain.product.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductTypeTest {
    @DisplayName("상품 타입이 재고 관련 타입인지 체크")
    @Test
    void containsStockTypeEx() {
        ProductType[] productTypes = ProductType.values();

        for (ProductType productType : productTypes) {
            if (productType == ProductType.HANDMADE) {
                boolean result = ProductType.containsStockType(productType);

                assertThat(result).isFalse();
            }
            if (productType == ProductType.BAKERY || productType == ProductType.BOTTLE) {
                boolean result = ProductType.containsStockType(productType);

                assertThat(result).isTrue();
            }
        }
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;
        // when
        boolean result = ProductType.containsStockType(givenType);
        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        // given
        ProductType givenType = ProductType.BAKERY;
        // when
        boolean result = ProductType.containsStockType(givenType);
        // then
        assertThat(result).isTrue();
    }
}