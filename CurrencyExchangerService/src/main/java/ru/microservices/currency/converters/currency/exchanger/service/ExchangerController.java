package ru.microservices.currency.converters.currency.exchanger.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@RestController
public class ExchangerController {

    private final Logger logger;
    private final Environment environment;

    public ExchangerController(Environment environment) {
        this.environment = environment;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        ExchangeValue exchangeValue = new ExchangeValue(from, to);
        exchangeValue.setPort(
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("local.server.port"))));

        if (Arrays.stream(CurrencyEnum.values()).noneMatch(currencyEnum -> currencyEnum.toString().equals(to)) ||
                Arrays.stream(CurrencyEnum.values()).noneMatch(currencyEnum -> currencyEnum.toString().equals(to))) {

            logger.error("Unsupported currency code: from= " + from + ", to= " + to);
            exchangeValue.setConversionMultiple(BigDecimal.valueOf(-1L));

        } else {

            ExchangeRateProvider ecbExchangeRateProvider = MonetaryConversions.getExchangeRateProvider("ECB");
            ExchangeRate rate = ecbExchangeRateProvider.getExchangeRate(from, to);
            exchangeValue.setConversionMultiple(BigDecimal.valueOf(rate.getFactor().doubleValueExact()));

            logger.info(exchangeValue.toString());
        }

        return exchangeValue;
    }
}
