package pe.exchange.test.domain.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeCurrencyQuery {

    @Column(value = "id")
    private Long id;

    @Column(value = "currencyOriginId")
    private Long currencyOriginId;

    @Column(value = "currencyTargetId")
    private Long currencyTargetId;

    @Column(value = "currencyOriginName")
    private String currencyOriginName;

    @Column(value = "currencyTargetName")
    private String currencyTargetName;

    @Column(value = "exchange")
    private BigDecimal exchange;

}
