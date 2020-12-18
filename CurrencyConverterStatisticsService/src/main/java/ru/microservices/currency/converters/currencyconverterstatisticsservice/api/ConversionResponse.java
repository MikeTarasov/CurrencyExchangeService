package ru.microservices.currency.converters.currencyconverterstatisticsservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ConversionResponse {

    private final String from;
    private final String to;
    private final long count;
    private final BigInteger amount;
}