package com.myorg.ps.service_impl;

import static com.myorg.ps.entity.mapper.ProductMapper.mapProductRequestToProduct;
import static com.myorg.ps.entity.mapper.ProductMapper.mapProductToProductResponse;
import static java.util.stream.Collectors.toList;

import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.dto.response.product.ProductResponse;
import com.myorg.ps.entity.mapper.ProductMapper;
import com.myorg.ps.entity.model.Product;
import com.myorg.ps.exception.InternalServerException;
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
    try {
      log.info("Proceeding to create a new product. {}", productRequest);

      Product product = this.productRepository.save(mapProductRequestToProduct(productRequest));
      log.info("Product successfully saved. {}", product);

      return mapProductToProductResponse(product);
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new InternalServerException(
          "Unexpected exception occurred while attempting to create a new product."
              + productRequest);
    }
  }

  @Override
  public List<ProductResponse> getAllProducts() {
    return this.productRepository.findAll()
        .stream()
        .map(ProductMapper::mapProductToProductResponse)
        .collect(toList());
  }

  @Override
  public ProductResponse getProductById(String id) {
    return this.productRepository
        .findById(id)
        .map(ProductMapper::mapProductToProductResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found for id: " + id));
  }
}
