package com.myorg.ps.service_impl;


import static com.myorg.ps.entity.mapper.DtoToEntityMapper.mapProductRequestToProduct;
import static com.myorg.ps.entity.mapper.EntityToDtoMapper.mapProductToProductResponse;
import static com.myorg.ps.entity.mapper.EntityToDtoMapper.mapProductsToProductResponses;
import static java.util.stream.Collectors.toList;

import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.dto.response.product.ProductResponse;
import com.myorg.ps.entity.mapper.EntityToDtoMapper;
import com.myorg.ps.entity.model.Product;
import com.myorg.ps.exception.RedundantPropertyException;
import com.myorg.ps.exception.ResourceNotFoundException;
import com.myorg.ps.integration.ProductRepository;
import com.myorg.ps.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "productService")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public ProductResponse save(ProductRequest productRequest) {
    if (existBySkuCode(productRequest.skuCode())) {
      throw new RedundantPropertyException("Provided skuCode: " + productRequest.skuCode()
          + " already exists and cannot be duplicated");
    }
    return mapProductToProductResponse(
        this.productRepository.save(mapProductRequestToProduct(productRequest)));
  }

  @Override
  public List<ProductResponse> getAllProducts() {
    return this.productRepository.findAll().stream()
        .map(EntityToDtoMapper::mapProductToProductResponse).collect(toList());
  }

  @Override
  public ProductResponse getProductById(String id) {
    return this.productRepository.findById(id).map(EntityToDtoMapper::mapProductToProductResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + id));
  }

  @Override
  public boolean existBySkuCode(String skuCode) {
    return this.productRepository.existsBySkuCode(skuCode);
  }

  @Override
  public List<ProductResponse> findBySkuCodeIn(List<String> skuCodes) {
    List<Product> products = productRepository.findBySkuCodeIn(skuCodes);

    if (products.isEmpty()) {
      throw new ResourceNotFoundException(
          "Product records not found for any of the skuCodes: " + skuCodes);
    }

    if (skuCodes.size() > products.size()) {
      skuCodes.removeAll(products.stream().map(Product::getSkuCode).toList());
      throw new ResourceNotFoundException(
          "Product records not found for some of the skuCodes: " + skuCodes);
    }

    return mapProductsToProductResponses(products);
  }
}
