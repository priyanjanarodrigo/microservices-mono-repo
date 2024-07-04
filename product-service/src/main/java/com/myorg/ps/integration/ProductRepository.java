package com.myorg.ps.integration;

import com.myorg.ps.entity.model.Product;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "productRepository")
public interface ProductRepository extends MongoRepository<Product, String> {

  boolean existsBySkuCode(String skuCode);

  List<Product> findBySkuCodeIn(List<String> skuCodes);
}
