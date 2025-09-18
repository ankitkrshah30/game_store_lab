package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.exception.MemberCreationException;
import com.supercell.gaming_store.exception.ResourceNotFoundException;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.service.interfaces.MembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MembersServiceImpl implements MembersService {

    private final MembersRepository membersRepository;

    public MembersServiceImpl(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    @Override
    public Members createMember(MembersRequest membersRequest) {
        try{
            log.info("Creating member with name: {}", membersRequest.getName());
            Members newMember=new Members();
            newMember.setName(membersRequest.getName());
            newMember.setPhoneNumber(membersRequest.getPhoneNumber());
            newMember.setBalance(0.0);
            log.info("Created member with name: {}", newMember.getName());
            return membersRepository.save(newMember);
        } catch (Exception e) {
            log.error("Error creating member: {}", e.getMessage());
            throw new MemberCreationException("Some Empty fields or the duplicate phone number");
        }
    }

    @Override
    public ResponseEntity<?> getMemberById(String id) {
        log.info("Getting member with id: {}", id);
        try{
            Members member=membersRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Member not found with id: "+id));
            log.info("Found member with id: {}", member.getId());
            return new ResponseEntity<>(member, HttpStatus.FOUND);
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
