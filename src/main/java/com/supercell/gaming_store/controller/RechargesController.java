package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.dto.RechargeRequest;
import com.supercell.gaming_store.entity.Recharges;
import com.supercell.gaming_store.service.interfaces.RechargesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recharges")
public class RechargesController {

    private final RechargesService rechargesService;

    public RechargesController(RechargesService rechargesService) {
        this.rechargesService = rechargesService;
    }

    @PostMapping
    public ResponseEntity<Recharges> rechargeAccount(@RequestBody RechargeRequest rechargeRequest) {
        // Get authenticated user's ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedMemberId = (String) authentication.getPrincipal();
        
        // Set the member ID from authentication context to ensure security
        rechargeRequest.setMemberId(authenticatedMemberId);
        
        Recharges recharge = rechargesService.performRecharge(rechargeRequest);
        return ResponseEntity.ok(recharge);
    }
}
