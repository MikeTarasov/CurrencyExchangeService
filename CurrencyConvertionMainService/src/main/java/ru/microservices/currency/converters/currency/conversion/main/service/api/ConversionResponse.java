package ru.microservices.currency.converters.currency.conversion.main.service.api;

import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
public class ConversionResponse {
    private final String from;
    private final String to;
    private final long count;
    private final BigInteger amount;
}
