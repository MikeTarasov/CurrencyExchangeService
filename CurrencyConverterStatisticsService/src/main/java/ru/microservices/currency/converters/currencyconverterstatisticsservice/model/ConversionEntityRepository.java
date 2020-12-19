package ru.microservices.currency.converters.currencyconverterstatisticsservice.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.api.ConversionResponse;

import java.util.List;

public interface ConversionEntityRepository extends JpaRepository<ConversionEntity, Long> {

    List<ConversionEntity> findByUserId(long userId);

    @Query(name = "SELECT user_id FROM currency_conversion GROUP BY user_id", nativeQuery = true)
    List<Long> getUsersId();

    @Query(name = "SELECT from_cur, to_cur, COUNT(*) AS count_conversions, SUM(quantity) AS total_amount " +
            "FROM currency_conversion GROUP BY (from_cur, to_cur) ORDER BY count_conversions desc, total_amount desc," +
            " from_cur", nativeQuery = true)
    List<ConversionResponse> getPopularRequestStatistics(Pageable pageable);
}
