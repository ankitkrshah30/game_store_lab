package com.supercell.gaming_store.repository;

import com.supercell.gaming_store.entity.Games;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends MongoRepository<Games, String> {
}
