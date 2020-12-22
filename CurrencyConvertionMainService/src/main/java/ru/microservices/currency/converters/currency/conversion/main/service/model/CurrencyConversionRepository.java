package ru.microservices.currency.converters.currency.conversion.main.service.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversionBean, Long> {
}
