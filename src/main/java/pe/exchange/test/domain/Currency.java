package pe.exchange.test.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Table(name = "currencies")
public class Currency {

    @Id
    private Long id;

    @Column(value = "name")
    private String name;

    @Column(value = "symbol")
    private String symbol;

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
