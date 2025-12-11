package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTransaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String machineId;
    private LocalDateTime timestamp;
    private Double totalDepositAmount;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<ReturnItem> items;
}
