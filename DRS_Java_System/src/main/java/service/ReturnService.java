package service;

import entity.BottleType;
import entity.ReturnItem;
import entity.ReturnTransaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import respository.BottleTypeRepository;
import respository.ReturnItemRepository;
import respository.ReturnTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class ReturnService
{
    private final BottleTypeRepository bottleTypeRepository;
    private final ReturnTransactionRepository transactionRepository;
    private final ReturnItemRepository itemRepository;

    public ReturnService(BottleTypeRepository bottleTypeRepository, ReturnTransactionRepository returnTransactionRepository, ReturnItemRepository returnItemRepository)
    {
        this.bottleTypeRepository = bottleTypeRepository;
        this.transactionRepository = returnTransactionRepository;
        this.itemRepository = returnItemRepository;
    }

    public ReturnTransaction startTransaction(String machineId)
    {
        ReturnTransaction tx = new ReturnTransaction();
        tx.setMachineId(machineId);
        tx.setTimestamp(LocalDateTime.now());
        tx.setTotalDepositAmount(0.0);

        return transactionRepository.save(tx);
    }

    public ReturnItem addItem(Long transactionId, Long bottleTypeId, int quantity)
    {
        ReturnTransaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        BottleType type = bottleTypeRepository.findById(bottleTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Bottle type not found"));

        ReturnItem item = new ReturnItem();
        item.setQuantity(quantity);
        item.setBottleType(type);
        item.setTransaction(tx);

        // Update total deposit amount
        BigDecimal depositPerPiece = type.getDepositValue();
        BigDecimal added = depositPerPiece.multiply(BigDecimal.valueOf(quantity));
        double newTotal = BigDecimal.valueOf(tx.getTotalDepositAmount())
                .add(added)
                .doubleValue();
        tx.setTotalDepositAmount(newTotal);

        return item;
    }

        public ReturnTransaction getTransaction(Long id)
        {
            return transactionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        }
}
