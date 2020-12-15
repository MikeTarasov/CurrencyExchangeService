package ru.microservices.currency.converters.currencyconverterstatisticsservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionEntityRepository extends JpaRepository<ConversionEntity, Long> {
}
