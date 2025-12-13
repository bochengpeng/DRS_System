package entity;

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

    public boolean validateQuantity() {
        return quantity > 0;
    }

    public boolean validateConsistency() {
        boolean hasValidBottleType = bottleType != null;
        boolean hasValidTransaction = transaction != null;
        boolean quantityValid = validateQuantity();
        boolean depositValid = bottleType != null && bottleType.validateDepositValue();

        return hasValidBottleType && hasValidTransaction && quantityValid && depositValid;
    }
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
