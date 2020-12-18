package ru.microservices.currency.converters.currency.conversion.main.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ExchangeResponse {

    private final long id;
    private final BigDecimal totalCalculatedAmount;
}
