package ru.microservices.currency.converters.currency.conversion.main.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserResponse {

    private final long userId;
    private final BigInteger amount;
    private final LocalDateTime time;
}