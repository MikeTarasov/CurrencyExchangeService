package ru.microservices.currency.converters.currency.conversion.main.service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.microservices.currency.converters.currency.conversion.main.service.api.ExchangeResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.api.UserStatsResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionRepository;
import ru.microservices.currency.converters.currency.conversion.main.service.proxy.CurrencyConverterStatisticsServiceProxy;
import ru.microservices.currency.converters.currency.conversion.main.service.proxy.CurrencyExchangeServiceProxy;

import java.math.BigDecimal;


@RestController
public class CurrencyConversionController {

    private final Logger logger;
    private final CurrencyExchangeServiceProxy exchangeProxy;
    private final CurrencyConverterStatisticsServiceProxy statisticsProxy;
    private final CurrencyConversionRepository repository;


    public CurrencyConversionController(CurrencyExchangeServiceProxy exchangeProxy,
                                        CurrencyConverterStatisticsServiceProxy statisticsProxy,
                                        CurrencyConversionRepository repository) {
        this.exchangeProxy = exchangeProxy;
        this.statisticsProxy = statisticsProxy;
        logger = LoggerFactory.getLogger(this.getClass());
        this.repository = repository;
    }


    @GetMapping("/exchange/user_id/{user_id}/quantity/{quantity}/from/{from}/to/{to}")
    public ResponseEntity<?> convertCurrency(@PathVariable("user_id") long userId, @PathVariable BigDecimal quantity,
                                             @PathVariable String from, @PathVariable String to) {

        CurrencyConversionBean response = exchangeProxy.retrieveExchangeValue(from, to);
        logger.info("{}", response);

        CurrencyConversionBean conversionBean = new CurrencyConversionBean(
                userId, from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
        repository.save(conversionBean);
        logger.info("Save " + conversionBean.toString());

        return ResponseEntity.status(200)
                .body(new ExchangeResponse(conversionBean.getId(), conversionBean.getTotalCalculatedAmount()));
    }

    @GetMapping("/stats/user-statistics/user-id/{user-id}/in-currency/{currency}")
    public ResponseEntity<?> getUserStatistics(@PathVariable("user-id") long userId,
                                               @PathVariable("currency") String currency) {
        UserStatsResponse userStatistics = statisticsProxy.getUserStatistics(userId, currency);
        logger.info(userStatistics.toString());
        return ResponseEntity.status(200).body(userStatistics);
    }
}