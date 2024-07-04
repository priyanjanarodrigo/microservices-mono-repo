package com.myorg.ps.controller;

import static com.myorg.ps.util.Constants.PRODUCT_BASE_URI;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.OK;

import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.dto.response.product.ProductResponse;
import com.myorg.ps.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = PRODUCT_BASE_URI)
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody ProductRequest productRequest,
      HttpServletRequest httpServletRequest) {
    ProductResponse productResponse = this.productService.save(productRequest);
    return ResponseEntity.created(
            create(httpServletRequest.getRequestURI() + "/" + productResponse.id()))
        .body(productResponse);
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAllProducts() {
    List<ProductResponse> productResponseList = this.productService.getAllProducts();
    return new ResponseEntity<>(productResponseList, OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> findProductById(
      @Valid
      @NotBlank(message = "{product.controller.pathVariable.id.notBlank}")
      @PathVariable("id") String id) {
    return ResponseEntity.ok(this.productService.getProductById(id));
  }

  @GetMapping("/skuCodes")
  public ResponseEntity<List<ProductResponse>> isInStock(
      @RequestParam("skuCodes") List<@NotBlank(message = "{product.controller.requestParams.skuCode.isBlank}") String> skuCodes) {
    return ResponseEntity.ok(productService.findBySkuCodeIn(skuCodes));
  }
}
