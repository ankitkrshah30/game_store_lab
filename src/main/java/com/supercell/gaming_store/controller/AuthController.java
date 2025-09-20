package com.supercell.gaming_store.controller;

import com.supercell.gaming_store.config.JwtUtil;
import com.supercell.gaming_store.dto.LoginRequest;
import com.supercell.gaming_store.dto.LoginResponse;
import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.pojo.Role;
import com.supercell.gaming_store.exception.ResourceNotFoundException;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.service.interfaces.MembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final MembersService membersService;
    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(MembersService membersService, MembersRepository membersRepository, 
                         PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.membersService = membersService;
        this.membersRepository = membersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MembersRequest membersRequest) {
        try {
            Members createdMember = membersService.createMember(membersRequest);
            String token = jwtUtil.generateToken(createdMember.getId(), createdMember.getPhoneNumber(), createdMember.getRoles());
            
            LoginResponse response = new LoginResponse(token, createdMember, "Registration successful");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody MembersRequest membersRequest) {
        try {
            // Add admin role to the request
            membersRequest.setRole("ADMIN");
            Members createdMember = membersService.createMember(membersRequest);
            String token = jwtUtil.generateToken(createdMember.getId(), createdMember.getPhoneNumber(), createdMember.getRoles());
            
            LoginResponse response = new LoginResponse(token, createdMember, "Admin registration successful");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Find member by phone number
            Members member = membersRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Member not found with phone number: " + loginRequest.getPhoneNumber()));

            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
                Map<String, Object> body = new HashMap<>();
                body.put("message", "Invalid credentials");
                return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
            }

            // Generate JWT token with roles
            String token = jwtUtil.generateToken(member.getId(), member.getPhoneNumber(), member.getRoles());
            
            LoginResponse response = new LoginResponse(token, member, "Login successful");
            return ResponseEntity.ok(response);
            
        } catch (ResourceNotFoundException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Invalid credentials");
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Login failed: " + e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7); // Remove "Bearer " prefix
            if (jwtUtil.validateToken(jwt)) {
                String memberId = jwtUtil.getMemberIdFromToken(jwt);
                String phoneNumber = jwtUtil.getPhoneNumberFromToken(jwt);
                Set<Role> roles = jwtUtil.getRolesFromToken(jwt);
                
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("memberId", memberId);
                response.put("phoneNumber", phoneNumber);
                response.put("roles", roles);
                response.put("expirationDate", jwtUtil.getExpirationDateFromToken(jwt));
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", false);
                response.put("message", "Invalid token");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", "Token validation failed");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
