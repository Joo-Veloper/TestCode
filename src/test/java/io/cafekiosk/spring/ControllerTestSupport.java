package io.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cafekiosk.spring.api.order.controller.OrderController;
import io.cafekiosk.spring.api.order.service.OrderService;
import io.cafekiosk.spring.api.product.controller.ProductController;
import io.cafekiosk.spring.api.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class
})

public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;

}
