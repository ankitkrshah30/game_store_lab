package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.dto.RechargeRequest;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.entity.Recharges;
import com.supercell.gaming_store.exception.ResourceNotFoundException;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.repository.RechargesRepository;
import com.supercell.gaming_store.service.interfaces.RechargesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Slf4j
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
        Recharges recharge=new Recharges();
        recharge.setMemberId(member);
        recharge.setAmount(amount);
        Recharges savedRecharge=rechargesRepository.save(recharge);
        if(member.getRecharges()!=null){
            member.getRecharges().add(savedRecharge);
        }
        else{
            member.setRecharges(new ArrayList<>());
            member.getRecharges().add(savedRecharge);
        }
        membersRepository.save(member);
        log.info("Recharge successful for recharge: {}", recharge);
        return savedRecharge;
    }
}
