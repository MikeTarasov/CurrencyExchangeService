package ru.microservices.currency.converters.currency.conversion.main.service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.microservices.currency.converters.currency.conversion.main.service.api.*;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionRepository;
import ru.microservices.currency.converters.currency.conversion.main.service.proxy.CurrencyConverterStatisticsServiceProxy;
import ru.microservices.currency.converters.currency.conversion.main.service.proxy.CurrencyExchangeServiceProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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
                                               @PathVariable String currency) {

        UserStatsResponse userStatistics = statisticsProxy.getUserStatistics(userId, currency);
        logger.info(userStatistics.toString());

        return ResponseEntity.status(200).body(userStatistics);
    }

    @GetMapping("/stats/single-request-more-than/{amount}/in-currency/{currency}")
    public ResponseEntity<?> getSingleRequestsMoreThan(@PathVariable BigDecimal amount,
                                                       @PathVariable String currency) {

        List<UserResponse> usersWithMaxSingleResponse = statisticsProxy.getUsersWithMaxSingleResponse(amount, currency);
        logger.info(usersWithMaxSingleResponse.toString());

        return ResponseEntity.status(200).body(usersWithMaxSingleResponse);
    }

    @GetMapping("/stats/total-amount-more-than/{amount}/in-currency/{currency}")
    public ResponseEntity<?> getUsersWithMaxAmount(@PathVariable BigDecimal amount,
                                                   @PathVariable String currency) {

        List<UserStatsResponse> usersWithMaxAmount = statisticsProxy.getUsersWithMaxAmount(amount, currency);
        logger.info(usersWithMaxAmount.toString());

        return ResponseEntity.status(200).body(usersWithMaxAmount);
    }

    @GetMapping("/stats/popular-requests/limit/{limit}")
    public ResponseEntity<?> getPopularRequestStatistics(@PathVariable int limit) {
        List<ConversionResponse> popularRequestStatistics = statisticsProxy.getPopularRequestStatistics(limit);
        logger.info(popularRequestStatistics.toString());

        return ResponseEntity.status(200).body(popularRequestStatistics);
    }

    @GetMapping("/stats/top-amount-requests/limit/{limit}")
    public ResponseEntity<?> getTopAmountRequestStatistics(@PathVariable("limit") int limit) {
        List<TopAmountConversionResponse> response = new ArrayList<>();

        for (CurrencyConversionBean conversion : statisticsProxy.getTopAmountRequestStatistics(limit)) {
            response.add(new TopAmountConversionResponse(conversion));
        }

        return ResponseEntity.status(200).body(response);
    }
}