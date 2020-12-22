package ru.microservices.currency.converters.currency.conversion.main.service.api;

import lombok.Data;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TopAmountConversionResponse {

    private final long userId;
    private final String from;
    private final String to;
    private final BigDecimal conversionMultiple;
    private final BigDecimal quantity;
    private final LocalDateTime time;

    public TopAmountConversionResponse(CurrencyConversionBean conversion) {
        userId = conversion.getUserId();
        from = conversion.getFrom();
        to = conversion.getTo();
        conversionMultiple = conversion.getConversionMultiple();
        quantity = conversion.getQuantity();
        time = conversion.getTime();
    }
}