package ru.microservices.currency.converters.currencyconverterstatisticsservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UserStatsResponse {

    private final long userId;
    private final long countOperations;
    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal avrAmount;
    private final BigDecimal totalAmount;
}