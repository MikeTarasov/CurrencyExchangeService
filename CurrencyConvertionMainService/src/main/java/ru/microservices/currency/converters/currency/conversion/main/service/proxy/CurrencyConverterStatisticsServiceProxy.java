package ru.microservices.currency.converters.currency.conversion.main.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.microservices.currency.converters.currency.conversion.main.service.api.ConversionResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.api.UserResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.api.UserStatsResponse;

import java.math.BigInteger;
import java.util.List;

@FeignClient(name = "statistics-service", url = "${host.statistics.service}")
public interface CurrencyConverterStatisticsServiceProxy {

    @GetMapping("/statistics/user_id/{user_id}")
    UserStatsResponse getUserStatistics(@PathVariable("user_id") String userId);

    @GetMapping("/statistics/single-request-more-than/{amount}")
    List<UserResponse> getUsersWithMaxSingleResponse(@PathVariable BigInteger amount);

    @GetMapping("/statistics/total-amount-more-than/{amount}")
    List<UserStatsResponse> getUsersWithMaxAmount(@PathVariable BigInteger amount);

    @GetMapping("/statistics/popular-requests")
    List<ConversionResponse> getPopularRequestStatistics();

    @GetMapping("/statistics/top-amount-requests")
    List<ConversionResponse> getTopAmountRequestStatistics();
}
