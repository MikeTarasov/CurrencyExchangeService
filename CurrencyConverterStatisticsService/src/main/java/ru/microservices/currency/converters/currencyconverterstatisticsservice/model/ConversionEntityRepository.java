package ru.microservices.currency.converters.currencyconverterstatisticsservice.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.microservices.currency.converters.currencyconverterstatisticsservice.projection.ConversionProjection;

import java.util.List;

@Repository
public interface ConversionEntityRepository extends JpaRepository<ConversionEntity, Long> {

    List<ConversionEntity> findByUserId(long userId);

    @Query(value = "SELECT DISTINCT (user_id) FROM currency_conversion", nativeQuery = true)
    List<Long> getUsersId();

    @Query(value = "SELECT from_cur AS fromCur, to_cur AS toCur, COUNT(*) AS count, SUM(quantity) AS total " +
            "FROM currency_conversion GROUP BY (from_cur, to_cur) ORDER BY count DESC, total DESC," +
            " from_cur", nativeQuery = true)
    List<ConversionProjection> getPopularRequestStatistics(Pageable pageable);
}