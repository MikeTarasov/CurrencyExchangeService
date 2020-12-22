package ru.microservices.currency.converters.currencyconverterstatisticsservice.projection;

import java.math.BigDecimal;

public interface ConversionProjection {

    String getFromCur();

    String getToCur();

    Long getCount();

    BigDecimal getTotal();
}