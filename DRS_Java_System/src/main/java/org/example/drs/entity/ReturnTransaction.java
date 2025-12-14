package org.example.drs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime timestamp;
    private BigDecimal totalDepositAmount;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<ReturnItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    public BigDecimal recalculateTotalDeposit()
    {
        BigDecimal total = BigDecimal.ZERO;

        if (items != null)
        {
            for (ReturnItem item : items)
            {
                if (item == null)
                {
                    continue;
                }

                BottleType type = item.getBottleType();
                if (type == null || type.getDepositValue() == null)
                {
                    continue;
                }

                BigDecimal perPiece = type.getDepositValue();
                BigDecimal itemTotal = perPiece.multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);
            }
        }

        this.totalDepositAmount = total;

        return total;
    }

    public void addItem(ReturnItem item)
    {
        if (items == null)
        {
            items = new ArrayList<>();
        }

        items.add(item);
        item.setTransaction(this);
        recalculateTotalDeposit();
    }

    public void removeItem(ReturnItem item)
    {
        if (item == null || items == null)
        {
            return;
        }

        if (items.remove(item))
        {
            item.setTransaction(null);
            recalculateTotalDeposit();
        }
    }

    public int getTotalItemCount()
    {
        int totalCount = 0;

        if (items == null)
        {
            return totalCount;
        }
        for (ReturnItem item : items)
        {
            if (item != null)
            {
                totalCount += item.getQuantity();
            }
        }

        return totalCount;
    }

    public boolean validateTransaction()
    {
        if (machine == null || machine.getStatus() != Status.ACTIVE)
        {
            return false;
        }

        if (items == null || items.isEmpty())
        {
            return false;
        }

        for (ReturnItem item : items)
        {
            if (item == null || !item.validateConsistency())
            {
                return false;
            }
        }

        if (totalDepositAmount == null || totalDepositAmount.compareTo(BigDecimal.ZERO) < 0)
        {
            return false;
        }

        return totalDepositAmount != null && totalDepositAmount.compareTo(BigDecimal.ZERO) >= 0;
    }

}
