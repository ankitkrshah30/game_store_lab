package com.supercell.gaming_store.service.interfaces;

import com.supercell.gaming_store.dto.RechargeRequest;
import com.supercell.gaming_store.entity.Recharges;

public interface RechargesService {
    Recharges performRecharge(RechargeRequest rechargeRequestDto);
}
