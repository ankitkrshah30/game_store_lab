package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.dto.PurchaseRequest;
import com.supercell.gaming_store.entity.Transactions;
import com.supercell.gaming_store.service.interfaces.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Transactions> purchaseGame(@RequestBody PurchaseRequest purchaseRequest) {
        Transactions transaction = transactionsService.purchaseGame(purchaseRequest);
        return ResponseEntity.ok(transaction);
    }
}
