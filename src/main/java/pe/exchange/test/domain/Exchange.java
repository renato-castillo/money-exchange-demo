package pe.exchange.test.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "exchanges")
public class Exchange {

    @Id
    private Long id;

    @Column(value = "currency_origin_id")
    private Long currencyOriginId;

    @Column(value = "currency_target_id")
    private Long currencyTargetId;

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
