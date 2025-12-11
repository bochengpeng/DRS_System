package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "bottle_type_id", nullable = false)
    private BottleType bottleType;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private ReturnTransaction transaction;
}

//Controller → Service → Repository → Database
//
//Controller handles HTTP requests
//
//Service contains the rules
//
//Repository does the CRUD operations
//
//Database stores the truth
