package pe.exchange.test.domain.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Getter
@Setter
public class UserExchangeQuery {

    @Column(value = "currencyOriginId")
    private Long currencyOriginId;

    @Column(value = "currencyTargetId")
    private Long currencyTargetId;

    @Column(value = "currencyOriginName")
    private String currencyOriginName;

    @Column(value = "currencyTargetName")
    private String currencyTargetName;

    @Column(value = "amount")
    private BigDecimal amount;

    @Column(value = "exchangeRate")
    private BigDecimal exchangeRate;

    @Column(value = "exchange")
    private BigDecimal exchange;


}
