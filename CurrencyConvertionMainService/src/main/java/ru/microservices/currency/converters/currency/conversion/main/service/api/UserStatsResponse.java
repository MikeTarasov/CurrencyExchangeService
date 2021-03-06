package ru.microservices.currency.converters.currency.conversion.main.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class UserStatsResponse {

    private final long userId;
    private final long countOperations;
    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal avrAmount;
    private final BigDecimal totalAmount;
}
