package com.supercell.gaming_store.service.interfaces;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;

import java.util.List;

public interface MembersService {
    Members createMember(MembersRequest membersRequest);
    Members getMemberById(String id);
    List<Members> getAllMembers();
}
