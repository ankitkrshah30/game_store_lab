package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.pojo.Role;
import com.supercell.gaming_store.exception.MemberCreationException;
import com.supercell.gaming_store.exception.ResourceNotFoundException;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.service.interfaces.MembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class MembersServiceImpl implements MembersService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;

    public MembersServiceImpl(MembersRepository membersRepository, PasswordEncoder passwordEncoder) {
        this.membersRepository = membersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Members createMember(MembersRequest membersRequest) {
        try{
            log.info("Creating member with name: {}", membersRequest.getName());
            
            // Validate required fields
            if (membersRequest.getName() == null || membersRequest.getName().trim().isEmpty()) {
                throw new MemberCreationException("Name is required");
            }
            if (membersRequest.getPhoneNumber() == null || membersRequest.getPhoneNumber().trim().isEmpty()) {
                throw new MemberCreationException("Phone number is required");
            }
            if (membersRequest.getPassword() == null || membersRequest.getPassword().trim().isEmpty()) {
                throw new MemberCreationException("Password is required");
            }

            Members newMember = new Members();
            newMember.setName(membersRequest.getName().trim());
            newMember.setPhoneNumber(membersRequest.getPhoneNumber().trim());
            newMember.setPassword(passwordEncoder.encode(membersRequest.getPassword()));
            newMember.setBalance(0.0);
            
            // Set roles
            Set<Role> roles = new HashSet<>();
            if (membersRequest.getRole() != null && "ADMIN".equalsIgnoreCase(membersRequest.getRole())) {
                roles.add(Role.ADMIN);
                roles.add(Role.USER); // Admin also has USER role
            } else {
                roles.add(Role.USER); // Default role
            }
            newMember.setRoles(roles);
            
            log.info("Created member with name: {} and roles: {}", newMember.getName(), roles);
            return membersRepository.save(newMember);
        } catch (Exception e) {
            log.error("Error creating member: {}", e.getMessage());
            throw new MemberCreationException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getMemberById(String id) {
        log.info("Getting member with id: {}", id);
        try{
            Members member = membersRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
            log.info("Found member with id: {}", member.getId());
            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Members> getAllMembers() {
        log.info("Getting all members");
        return membersRepository.findAll();
    }
}
