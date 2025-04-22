package io.cafekiosk.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Toy project",
        version = "v1",
        description = "A toy project used for adding and practicing test code"
))
public class CafekioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafekioskApplication.class, args);
    }

}
