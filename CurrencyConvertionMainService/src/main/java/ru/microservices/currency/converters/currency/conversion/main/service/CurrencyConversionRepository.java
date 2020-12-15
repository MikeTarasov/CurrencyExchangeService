package ru.microservices.currency.converters.currency.conversion.main.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversionBean, Long> {
}
