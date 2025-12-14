package org.example.drs.service;

import org.example.drs.entity.BottleType;
import org.example.drs.entity.Machine;
import org.example.drs.entity.ReturnItem;
import org.example.drs.entity.ReturnTransaction;
import jakarta.transaction.Transactional;
import org.example.drs.repository.MachineRepository;
import org.springframework.stereotype.Service;
import org.example.drs.repository.BottleTypeRepository;
import org.example.drs.repository.ReturnItemRepository;
import org.example.drs.repository.ReturnTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
public class ReturnService {

    private final BottleTypeRepository bottleTypeRepository;
    private final ReturnTransactionRepository transactionRepository;
    private final ReturnItemRepository itemRepository;
    private final MachineRepository machineRepository;

    public ReturnService(BottleTypeRepository bottleTypeRepository,
                         ReturnTransactionRepository transactionRepository,
                         ReturnItemRepository itemRepository,
                         MachineRepository machineRepository) {
        this.bottleTypeRepository = bottleTypeRepository;
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.machineRepository = machineRepository;
    }

    public ReturnTransaction startTransaction(String machineId) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Machine not found"));

        ReturnTransaction tx = new ReturnTransaction();
        tx.setMachine(machine);
        tx.setTimestamp(LocalDateTime.now());
        tx.setTotalDepositAmount(BigDecimal.ZERO);
        tx.setItems(new ArrayList<>());

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

        if (tx.getItems() == null) {
            tx.setItems(new java.util.ArrayList<>());
        }
        tx.getItems().add(item);

        BigDecimal currentTotal = tx.getTotalDepositAmount();
        if (currentTotal == null) {
            currentTotal = BigDecimal.ZERO;
        }

        BigDecimal depositPerPiece = type.getDepositValue();
        if (depositPerPiece == null) {
            depositPerPiece = BigDecimal.ZERO;
        }
        BigDecimal itemTotal = depositPerPiece.multiply(BigDecimal.valueOf(quantity));
        BigDecimal newTotal = currentTotal.add(itemTotal);
        tx.setTotalDepositAmount(newTotal);
        transactionRepository.save(tx);

        return item;
    }

        public ReturnTransaction getTransaction(Long id)
        {
            return transactionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        }
}
