package ru.microservices.currency.converters.currencyconverterstatisticsservice.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.api.UserResponse;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.api.UserStatsResponse;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.model.ConversionEntity;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.model.ConversionEntityRepository;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.proxy.CurrencyExchangeServiceProxy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
public class StatisticsController {

    private final Logger logger;
    private final ConversionEntityRepository repository;
    private final CurrencyExchangeServiceProxy exchangeServiceProxy;


    public StatisticsController(ConversionEntityRepository repository,
                                CurrencyExchangeServiceProxy exchangeServiceProxy) {
        this.repository = repository;
        logger = LoggerFactory.getLogger(this.getClass());
        this.exchangeServiceProxy = exchangeServiceProxy;
    }

    @GetMapping("/statistics/user_id/{user_id}/in-currency/{currency}")
    public UserStatsResponse getUserStatistics(@PathVariable("user_id") long userId,
                                               @PathVariable("currency") String currency) {

        UserStatsResponse userStatsResponse = getUserStatsResponseByUserIdAndCurrency(userId, currency);

        logger.info(userStatsResponse.toString());

        return userStatsResponse;
    }

    @GetMapping("/statistics/single-request-more-than/{amount}/in-currency/{currency}")
    public List<UserResponse> getSingleRequestsMoreThan(@PathVariable("amount") BigDecimal amount,
                                                        @PathVariable("currency") String currency) {
        List<UserResponse> result = new ArrayList<>();
        for (ConversionEntity conversion : repository.findAll()) {
            BigDecimal quantity = conversion.getQuantity();

            if (!conversion.getFrom().equals(currency)) {
                quantity = quantity.multiply(exchangeServiceProxy
                        .retrieveExchangeValue(conversion.getFrom(), currency).getConversionMultiple());
            }

            if (quantity.compareTo(amount) >= 0) {
                result.add(new UserResponse(conversion.getUserId(), quantity, conversion.getTime()));
            }
        }
        result.sort(Comparator.comparing(UserResponse::getAmount));

        return result;
    }

    @GetMapping("/statistics/total-amount-more-than/{amount}/in-currency/{currency}")
    public List<UserStatsResponse> getUsersWithMaxAmount(@PathVariable("amount") BigDecimal amount,
                                                         @PathVariable("currency") String currency) {
        List<UserStatsResponse> result = new ArrayList<>();

        List<Long> usersId = repository.getUsersId();

        for (long userId : usersId) {

            UserStatsResponse userStatsResponse = getUserStatsResponseByUserIdAndCurrency(userId, currency);

            if (userStatsResponse.getMinAmount().compareTo(amount) >= 0) {
                result.add(userStatsResponse);
            }
        }

        result.sort(Comparator.comparing(UserStatsResponse::getTotalAmount));

        return result;
    }

//
//    @GetMapping("/statistics/popular-requests/limit/{limit}")
//    public List<ConversionResponse> getPopularRequestStatistics(@PathVariable("limit") long limit) {
//        return null;
//    }
//
//    @GetMapping("/statistics/top-amount-requests/limit/{limit}")
//    public List<ConversionResponse> getTopAmountRequestStatistics(@PathVariable("limit") long limit) {
//        return null;
//    }

    private UserStatsResponse getUserStatsResponseByUserIdAndCurrency(long userId, String currency) {
        List<ConversionEntity> conversions = repository.findByUserId(userId);

        BigDecimal minAmount = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal maxAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ConversionEntity conversion : conversions) {
            BigDecimal quantity = conversion.getQuantity();

            if (!conversion.getFrom().equals(currency)) {
                quantity = quantity.multiply(exchangeServiceProxy
                        .retrieveExchangeValue(conversion.getFrom(), currency).getConversionMultiple());
            }

            totalAmount = totalAmount.add(quantity);
            minAmount = minAmount.min(quantity);
            maxAmount = maxAmount.max(quantity);
        }
        long countOperations = conversions.size();
        BigDecimal avrAmount = totalAmount.divide(BigDecimal.valueOf(countOperations), MathContext.DECIMAL64);


        return new UserStatsResponse(userId, countOperations, minAmount, maxAmount, avrAmount, totalAmount);
    }
}