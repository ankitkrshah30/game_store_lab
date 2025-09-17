package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.service.interfaces.MembersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @PostMapping
    public ResponseEntity<Members> createMember(@RequestBody MembersRequest membersRequest) {
        Members createdMember = membersService.createMember(membersRequest);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Members>> getAllMembers() {
        List<Members> members = membersService.getAllMembers();
        return ResponseEntity.ok(members);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Members> getMemberById(@PathVariable String id) {
        Members member = membersService.getMemberById(id);
        return ResponseEntity.ok(member);
    }
}

// http://localhost:8080/api/members