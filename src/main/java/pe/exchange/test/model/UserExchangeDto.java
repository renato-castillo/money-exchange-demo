package pe.exchange.test.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserExchangeDto {

    private Long currencyOriginId;

    private Long currencyTargetId;

    private String currencyOriginName;

    private String currencyTargetName;

    private BigDecimal amount;

    private BigDecimal exchangeRate;
    private BigDecimal exchange;

}
