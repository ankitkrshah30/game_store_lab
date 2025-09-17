package com.supercell.gaming_store.dto;

import lombok.Data;

@Data
public class RechargeRequest {
    private String memberId;
    private Double amount;
}
