package ru.microservices.currency.converters.currency.conversion.main.service.model;

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
public class CurrencyConversionBean {

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


    public CurrencyConversionBean(Long userId, String from, String to, BigDecimal conversionMultiple, BigDecimal quantity,
                                  BigDecimal totalCalculatedAmount, int port) {
        super();
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
        this.quantity = quantity;
        this.totalCalculatedAmount = totalCalculatedAmount;
        this.port = port;
        time = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", userId=" + userId +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", conversionMultiple=" + conversionMultiple +
                ", quantity=" + quantity +
                ", totalCalculatedAmount=" + totalCalculatedAmount +
                ", time=" + time +
                '}';
    }

    public String toResponseString() {
        return "{userId=".concat(String.valueOf(userId)).concat(", from='").concat(from).concat("', to='")
                .concat(to).concat("', conversionMultiple=").concat(String.valueOf(conversionMultiple))
                .concat(", quantity=").concat(String.valueOf(quantity)).concat(", time=").concat(String.valueOf(time))
                .concat("}");
    }
}