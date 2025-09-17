package com.supercell.gaming_store.repository;

import com.supercell.gaming_store.entity.Recharges;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargesRepository extends MongoRepository<Recharges, String> {
}
