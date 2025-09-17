package com.supercell.gaming_store.repository;

import com.supercell.gaming_store.entity.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends MongoRepository<Transactions,String> {
}
