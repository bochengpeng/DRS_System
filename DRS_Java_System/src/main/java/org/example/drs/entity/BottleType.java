package org.example.drs.entity;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bottle_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BottleType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal depositValue;

    public BigDecimal calculateTotalDeposit(int quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (depositValue == null) {
            throw new IllegalStateException("Deposit value is not set");
        }

        return depositValue.multiply(BigDecimal.valueOf(quantity));
    }

    public boolean validateDepositValue() {
        return depositValue != null && depositValue.compareTo(BigDecimal.ZERO) >= 0;
    }
}
