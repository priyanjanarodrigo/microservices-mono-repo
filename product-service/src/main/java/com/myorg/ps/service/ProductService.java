package com.myorg.ps.service;

import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.dto.response.product.ProductResponse;
import java.util.List;

public interface ProductService {

  ProductResponse save(ProductRequest productRequest);

  List<ProductResponse> getAllProducts();

  ProductResponse getProductById(String id);
}
