package ru.microservices.currency.converters.currencyconverterstatisticsservice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversionEntityRepository extends JpaRepository<ConversionEntity, Long> {

    List<ConversionEntity> findByUserId(@Param("user_id") long userId);
}
