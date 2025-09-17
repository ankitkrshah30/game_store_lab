package com.supercell.gaming_store.service.impl;

import com.supercell.gaming_store.dto.PurchaseRequest;
import com.supercell.gaming_store.entity.Games;
import com.supercell.gaming_store.entity.Members;
import com.supercell.gaming_store.entity.Transactions;
import com.supercell.gaming_store.exception.InsufficientBalanceException;
import com.supercell.gaming_store.exception.ResourceNotFoundException;
import com.supercell.gaming_store.repository.GamesRepository;
import com.supercell.gaming_store.repository.MembersRepository;
import com.supercell.gaming_store.repository.TransactionsRepository;
import com.supercell.gaming_store.service.interfaces.TransactionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final MembersRepository membersRepository;
    private final GamesRepository gamesRepository;
    private final TransactionsRepository transactionsRepository;

    public TransactionsServiceImpl(MembersRepository membersRepository, GamesRepository gamesRepository, TransactionsRepository transactionsRepository) {
        this.membersRepository = membersRepository;
        this.gamesRepository = gamesRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    @Transactional
    public Transactions purchaseGame(PurchaseRequest purchaseRequest) {
        String memberId = purchaseRequest.getMemberId();
        String gameId = purchaseRequest.getGameId();
        Members member=membersRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
        Games game=gamesRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameId));
        if(member.getBalance()<game.getPrice()){
        throw new InsufficientBalanceException("Insufficient balance to purchase " + game.getName());
        }
        double newBalance=member.getBalance()-game.getPrice();
        member.setBalance(newBalance);
        membersRepository.save(member);
        Transactions transaction=new Transactions();
        transaction.setMemberId(member);
        transaction.setGame(game);
        transaction.setAmount(game.getPrice());
        return transactionsRepository.save(transaction);
    }
}
