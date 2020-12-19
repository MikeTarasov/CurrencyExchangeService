package ru.microservices.currency.converters.currency.conversion.main.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.microservices.currency.converters.currency.conversion.main.service.api.ConversionResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.api.UserResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.api.UserStatsResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "statistics-service", url = "${host.statistics.service}")
public interface CurrencyConverterStatisticsServiceProxy {

    @GetMapping("/statistics/user_id/{user_id}/in-currency/{currency}")
    UserStatsResponse getUserStatistics(@PathVariable("user_id") long userId,
                                        @PathVariable("currency") String currency);

    @GetMapping("/statistics/single-request-more-than/{amount}/in-currency/{currency}")
    List<UserResponse> getUsersWithMaxSingleResponse(@PathVariable("amount") BigDecimal amount,
                                                     @PathVariable("currency") String currency);

    @GetMapping("/statistics/total-amount-more-than/{amount}/in-currency/{currency}")
    List<UserStatsResponse> getUsersWithMaxAmount(@PathVariable("amount") BigDecimal amount,
                                                  @PathVariable("currency") String currency);

    @GetMapping("/statistics/popular-requests/limit/{limit}")
    List<ConversionResponse> getPopularRequestStatistics(@PathVariable("limit") int limit);

    @GetMapping("/statistics/top-amount-requests/limit/{limit}")
    List<CurrencyConversionBean> getTopAmountRequestStatistics(@PathVariable("limit") int limit);
}
