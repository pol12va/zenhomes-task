package io.alexanderkam.zenhomestask.entity;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "electricity_counter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = "id")
public class ElectricityCounterEntity {

    @Id
    private long id;

    @NotBlank
    @Column
    private String villageName;

    @NotNull
    @Column(precision = 8, scale = 2)
    private BigDecimal consumption;

    @UpdateTimestamp
    private LocalDateTime updateTs;

}
