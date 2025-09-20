package com.supercell.gaming_store.repository;

import com.supercell.gaming_store.entity.Members;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends MongoRepository<Members, String> {
    Optional<Members> findByPhoneNumber(String phoneNumber);
}
