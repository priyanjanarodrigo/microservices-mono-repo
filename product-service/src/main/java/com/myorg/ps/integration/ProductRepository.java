package com.myorg.ps.integration;

import com.myorg.ps.entity.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "productRepository")
public interface ProductRepository extends MongoRepository<Product, String> {

}
