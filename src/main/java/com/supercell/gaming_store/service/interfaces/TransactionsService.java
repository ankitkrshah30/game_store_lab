package com.supercell.gaming_store.service.interfaces;

import com.supercell.gaming_store.dto.PurchaseRequest;
import com.supercell.gaming_store.entity.Transactions;

public interface TransactionsService {

    Transactions purchaseGame(PurchaseRequest purchaseRequestDto);

}
