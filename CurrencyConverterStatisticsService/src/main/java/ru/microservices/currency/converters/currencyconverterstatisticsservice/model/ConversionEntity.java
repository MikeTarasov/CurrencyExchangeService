package ru.microservices.currency.converters.currencyconverterstatisticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency_conversion")
public class ConversionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "from_cur", nullable = false)
    private String from;
    @Column(name = "to_cur", nullable = false)
    private String to;
    @Column(columnDefinition = "numeric(32, 16)")
    private BigDecimal conversionMultiple;
    private BigDecimal quantity;
    @Column(columnDefinition = "numeric(32, 16)")
    private BigDecimal totalCalculatedAmount;
    private LocalDateTime time;
    @Transient
    private int port;
}
