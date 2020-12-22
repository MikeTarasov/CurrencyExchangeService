package ru.microservices.currency.converters.currencyconverterstatisticsservice.api;

import lombok.Data;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.projection.ConversionProjection;

import java.math.BigDecimal;

@Data
public class ConversionResponse {

    private final String from;
    private final String to;
    private final long countConversions;
    private final BigDecimal totalAmount;

    public ConversionResponse(ConversionProjection projection) {
        from = projection.getFromCur();
        to = projection.getToCur();
        countConversions = projection.getCount();
        totalAmount = projection.getTotal();
    }
}