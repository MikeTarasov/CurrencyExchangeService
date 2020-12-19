package ru.microservices.currency.converters.currencyconverterstatisticsservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConversionResponse {

    private final String from;
    private final String to;
    private final long countConversions;
    private final BigDecimal totalAmount;
}