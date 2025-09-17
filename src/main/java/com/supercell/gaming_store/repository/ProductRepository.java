package com.supercell.gaming_store.repository;

import com.supercell.gaming_store.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
