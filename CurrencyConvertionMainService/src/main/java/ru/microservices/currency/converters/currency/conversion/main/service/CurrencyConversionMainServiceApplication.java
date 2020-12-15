package ru.microservices.currency.converters.currency.conversion.main.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("ru.microservices.currency.converters.currency.conversion.main.service.proxy")
@EnableDiscoveryClient
public class CurrencyConversionMainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConversionMainServiceApplication.class, args);
    }

}
