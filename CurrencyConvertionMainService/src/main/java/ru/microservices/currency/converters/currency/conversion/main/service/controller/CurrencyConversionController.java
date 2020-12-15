package ru.microservices.currency.converters.currency.conversion.main.service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.microservices.currency.converters.currency.conversion.main.service.api.ExchangeResponse;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionRepository;
import ru.microservices.currency.converters.currency.conversion.main.service.proxy.CurrencyConverterStatisticsServiceProxy;
import ru.microservices.currency.converters.currency.conversion.main.service.proxy.CurrencyExchangeServiceProxy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CurrencyConversionController {
    private final Logger logger;
    private final CurrencyExchangeServiceProxy exchangeProxy;
    private final CurrencyConverterStatisticsServiceProxy statisticsProxy;
    private final CurrencyConversionRepository repository;

    @Value("${host.exchange.service}")
    private String hostExchangeService;

    public CurrencyConversionController(CurrencyExchangeServiceProxy exchangeProxy,
                                        CurrencyConverterStatisticsServiceProxy statisticsProxy,
                                        CurrencyConversionRepository repository) {
        this.exchangeProxy = exchangeProxy;
        this.statisticsProxy = statisticsProxy;
        logger = LoggerFactory.getLogger(this.getClass());
        this.repository = repository;
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                       @PathVariable BigDecimal quantity) {
        CurrencyConversionBean response = exchangeProxy.retrieveExchangeValue(from, to);
        logger.info("{}", response);
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

    @GetMapping("/exchange/user_id/{user_id}/quantity/{quantity}/from/{from}/to/{to}")
    public ResponseEntity<?> convertCurrency(@PathVariable("user_id") long userId, @PathVariable BigDecimal quantity,
                                             @PathVariable String from, @PathVariable String to) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://".concat(hostExchangeService).concat("/currency-exchange/from/{from}/to/{to}"),
                CurrencyConversionBean.class, uriVariables);
        CurrencyConversionBean response = responseEntity.getBody();
        assert response != null;

        CurrencyConversionBean conversionBean = new CurrencyConversionBean(
                userId, from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
        repository.save(conversionBean);

        return ResponseEntity.status(200)
                .body(new ExchangeResponse(conversionBean.getId(), conversionBean.getTotalCalculatedAmount()));
    }
}