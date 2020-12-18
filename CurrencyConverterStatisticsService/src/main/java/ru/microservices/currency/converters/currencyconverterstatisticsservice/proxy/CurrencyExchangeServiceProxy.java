package ru.microservices.currency.converters.currencyconverterstatisticsservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.model.ConversionEntity;

@FeignClient(name = "exchange-service", url = "${host.exchange.service}")
public interface CurrencyExchangeServiceProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    ConversionEntity retrieveExchangeValue
            (@PathVariable("from") String from, @PathVariable("to") String to);
}