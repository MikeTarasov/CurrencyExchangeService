package ru.microservices.currency.converters.currency.conversion.main.service.api;

import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
public class UserStatsResponse {

    private final long userId;
    private final BigInteger totalAmount;
}
