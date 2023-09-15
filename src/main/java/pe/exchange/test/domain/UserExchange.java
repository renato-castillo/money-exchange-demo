package pe.exchange.test.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Table(name = "user_exchanges")
public class UserExchange {

    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "exchange_id")
    private Long exchangeId;

    @Column(value = "amount")
    private BigDecimal amount;

    @Column(value = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(value = "exchange")
    private BigDecimal exchange;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(value = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(value = "created_by")
    private String createdBy;

    @Column(value = "modified_by")
    private String modifiedBy;
}
