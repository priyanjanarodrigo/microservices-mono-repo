package com.myorg.ps.entity.mapper;

import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoToEntityMapper {

  /**
   * Maps ProductRequest to Product and returns.
   *
   * @param productRequest ProductRequest object
   * @return Product
   */
  public static Product mapProductRequestToProduct(ProductRequest productRequest) {
    return Product.builder()
        .name(productRequest.name())
        .description(productRequest.description())
        .price(productRequest.price())
        .skuCode(productRequest.skuCode())
        .build();
  }
}
