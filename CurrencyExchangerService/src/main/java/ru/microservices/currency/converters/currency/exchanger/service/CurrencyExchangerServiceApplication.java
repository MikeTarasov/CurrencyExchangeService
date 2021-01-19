package ru.microservices.currency.converters.currency.exchanger.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
//@OpenAPIDefinition(info =
//@Info(title = "Currency Exchanger Service API", version = "1.0",
//        description = "Documentation Currency Exchanger Service API v1.0"))
public class CurrencyExchangerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangerServiceApplication.class, args);
    }

//    @Bean
//    public Docket swaggerPersonApi10() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors
//                        .basePackage("ru.microservices.currency.converters.currency.exchanger.service"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(new ApiInfoBuilder()
//                        .version("1.0")
//                        .title("Currency Exchanger Service API")
//                        .description("Documentation Currency Exchanger Service API v1.0")
//                        .build());
//    }
}