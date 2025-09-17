package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.dto.MembersRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.service.interfaces.MembersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembersServiceImpl implements MembersService {

    private final MembersRepository membersRepository;

    public MembersServiceImpl(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    @Override
    public Members createMember(MembersRequest membersRequest) {
        Members newMember=new Members();
        newMember.setName(membersRequest.getName());
        newMember.setPhoneNumber(membersRequest.getPhoneNumber());
        newMember.setBalance(0.0);
        return membersRepository.save(newMember);
    }

    @Override
    public Members getMemberById(String id) {
        return membersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Members> getAllMembers() {
        return membersRepository.findAll();
    }

}
