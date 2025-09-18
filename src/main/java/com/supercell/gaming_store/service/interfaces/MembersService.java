package com.supercell.gaming_store.service.interfaces;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MembersService {
    Members createMember(MembersRequest membersRequest);
    ResponseEntity<?> getMemberById(String id);
    List<Members> getAllMembers();
}
