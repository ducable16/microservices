package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> addProduct(@RequestBody Product request) {
        return ResponseEntity.ok(productRepository.insert(request));
    }
}
