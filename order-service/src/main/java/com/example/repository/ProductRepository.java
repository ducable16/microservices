package com.example.repository;

import com.example.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
    Optional<Product> findByProductID(String productID);

    Optional<Product> removeProductByProductID(String productID);

    Optional<Product> findByProductName(String productName);

}
