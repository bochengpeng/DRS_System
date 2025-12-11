package controller;

import entity.ReturnItem;
import entity.ReturnTransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ReturnService;

@RestController
@RequestMapping("/api/returns")
public class ReturnController
{
    private final ReturnService returnService;
    public ReturnController(ReturnService returnService)
    {
        this.returnService = returnService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<ReturnTransaction> startTransaction(@RequestParam String machineId)
    {
        return ResponseEntity.ok(returnService.startTransaction(machineId));
    }

    @PostMapping("/transactions/{transactionId}/items")
    public ResponseEntity<ReturnItem> addItem(
            @RequestParam Long transactionId,
            @RequestParam Long bottleTypeId,
            @RequestParam int quantity)
    {
        return ResponseEntity.ok(
                returnService.addItem(transactionId, bottleTypeId, quantity));
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<ReturnTransaction> getTransaction(@PathVariable Long transactionId)
    {
        return ResponseEntity.ok(returnService.getTransaction(transactionId));
    }

}
