package com.chay.product.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chay.product.service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
