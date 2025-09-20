package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.service.interfaces.MembersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Members>> getAllMembers() {
        List<Members> members = membersService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == authentication.principal)")
    public ResponseEntity<?> getMemberById(@PathVariable String id) {
        return membersService.getMemberById(id);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getCurrentMemberProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = (String) authentication.getPrincipal();
        return membersService.getMemberById(memberId);
    }
}