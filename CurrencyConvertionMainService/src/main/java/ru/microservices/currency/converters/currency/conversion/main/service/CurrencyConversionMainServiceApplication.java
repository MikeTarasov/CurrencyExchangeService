package ru.microservices.currency.converters.currency.conversion.main.service;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients("ru.microservices.currency.converters.currency.conversion.main.service.proxy")
@EnableDiscoveryClient
@EnableSwagger2
@OpenAPIDefinition(info = @Info(title = "Currency conversion API", version = "1.0",
        description = "Documentation Currency conversion API v1.0"))
public class CurrencyConversionMainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConversionMainServiceApplication.class, args);
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("ru.microservices.currency.converters.currency.conversion.main.service.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("Currency conversion API")
                        .description("Documentation Currency conversion API v1.0").build());
    }
}
