package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.dto.RechargeRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.entity.Recharges;
import com.supercell.gaming_store.exception.ResourceNotFoundException;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.repository.RechargesRepository;
import com.supercell.gaming_store.service.interfaces.RechargesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RechargesServiceImpl implements RechargesService {
    private final MembersRepository membersRepository;
    private final RechargesRepository rechargesRepository;

    public RechargesServiceImpl(MembersRepository membersRepository, RechargesRepository rechargesRepository) {
        this.membersRepository = membersRepository;
        this.rechargesRepository = rechargesRepository;
    }

    @Override
    @Transactional
    public Recharges performRecharge(RechargeRequest rechargeRequest) {
        String memberId = rechargeRequest.getMemberId();
        Double amount = rechargeRequest.getAmount();
        Members member = membersRepository.findById(memberId)
                .orElseThrow(()->new ResourceNotFoundException("Member not found with id: " + memberId));
        member.setBalance(member.getBalance()+amount);
        membersRepository.save(member);
        Recharges recharge=new Recharges();
        recharge.setMemberId(member);
        recharge.setAmount(amount);

        return rechargesRepository.save(recharge);
    }
}
