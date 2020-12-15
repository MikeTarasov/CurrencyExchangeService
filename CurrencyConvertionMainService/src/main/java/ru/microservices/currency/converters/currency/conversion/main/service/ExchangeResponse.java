package ru.microservices.currency.converters.currency.conversion.main.service;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class ExchangeResponse {

    private final long id;
    private final BigDecimal totalCalculatedAmount;
}
