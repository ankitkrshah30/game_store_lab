package com.supercell.gaming_store.dto;

import lombok.Data;

@Data
public class PurchaseRequest {

    private String memberId;
    private String gameId;

}
