package pe.exchange.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeDto {

    private Long id;

    private Long currencyOriginId;

    private Long currencyTargetId;

    private String currencyOriginName;

    private String currencyTargetName;

    private BigDecimal exchange;

}
