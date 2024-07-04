package com.myorg.ps.entity.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.myorg.ps.entity.dto.response.product.ProductResponse;
import com.myorg.ps.entity.model.Product;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class EntityToDtoMapper {

  /**
   * Maps Product to ProductResponse and returns it.
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
        .skuCode(product.getSkuCode())
        .build();
  }

  /**
   * Maps a list of Product to a list of ProductResponses and returns it.
   *
   * @param products List of Products
   * @return List<ProductResponse>
   */
  public static List<ProductResponse> mapProductsToProductResponses(List<Product> products) {
    return products.stream()
        .map(EntityToDtoMapper::mapProductToProductResponse)
        .toList();
  }
}
