package ru.microservices.currency.converters.currencyconverterstatisticsservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {

    private final long userId;
    private final BigInteger amount;
    private final LocalDateTime time;
}