package ru.microservices.currency.converters.currencyconverterstatisticsservice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversionEntityRepository extends JpaRepository<ConversionEntity, Long> {

    List<ConversionEntity> findByUserId(long userId);

    @Query(name = "SELECT user_id FROM currency_conversion GROUP BY user_id", nativeQuery = true)
    List<Long> getUsersId();
}
