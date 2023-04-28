package com.myorg.ps.entity.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.dto.response.product.ProductResponse;
import com.myorg.ps.entity.model.Product;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ProductMapper {

  /**
   * Maps ProductRequest to Product and returns.
   *
   * @param productRequest ProductRequest object
   * @return Product
   */
  public static Product mapProductRequestToProduct(ProductRequest productRequest) {
    return Product.builder()
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .build();
  }

  /**
   * Maps Product to ProductResponse and returns.
   *
   * @param product Product object
   * @return ProductResponse
   */
  public static ProductResponse mapProductToProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();
  }
}
