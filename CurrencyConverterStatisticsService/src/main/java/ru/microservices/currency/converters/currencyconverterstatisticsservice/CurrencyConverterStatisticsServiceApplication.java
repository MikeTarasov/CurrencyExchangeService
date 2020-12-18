package ru.microservices.currency.converters.currencyconverterstatisticsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("ru.microservices.currency.converters.currencyconverterstatisticsservice.proxy")
@EnableDiscoveryClient
public class CurrencyConverterStatisticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterStatisticsServiceApplication.class, args);
    }
}