package Entity;

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
}
