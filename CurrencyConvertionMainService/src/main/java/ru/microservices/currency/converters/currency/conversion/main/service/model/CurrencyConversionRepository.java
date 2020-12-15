package ru.microservices.currency.converters.currency.conversion.main.service.model;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.microservices.currency.converters.currency.conversion.main.service.model.CurrencyConversionBean;

public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversionBean, Long> {
}
