package ru.microservices.currency.converters.currency.exchanger.service;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeValue {

    private String from;
    private String to;
    private BigDecimal conversionMultiple;
    private int port;

    public ExchangeValue(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Successfully convert from ".concat(from).concat(", to ").concat(to).concat(", multiple = ")
                .concat(conversionMultiple.toString());
    }
}