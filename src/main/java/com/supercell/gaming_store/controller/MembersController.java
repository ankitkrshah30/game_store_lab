package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.service.interfaces.MembersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MembersRequest membersRequest) {
        try{
            Members createdMember = membersService.createMember(membersRequest);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Created the user successfully");
            body.put("member", createdMember);
            return new ResponseEntity<>(
                    body,
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message",e.getMessage());
            return new ResponseEntity<>(
                    body,
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    @GetMapping
    public ResponseEntity<List<Members>> getAllMembers() {
        List<Members> members = membersService.getAllMembers();
        return ResponseEntity.ok(members);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable String id) {
        return membersService.getMemberById(id);
    }
}

// http://localhost:8080/api/members