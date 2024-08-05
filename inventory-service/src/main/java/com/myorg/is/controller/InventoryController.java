package com.myorg.is.controller;

import static com.myorg.is.util.Constants.INVENTORY_BASE_URI;
import static com.myorg.is.validation.BatchRequestValidator.getQuantityReduceBatchRequestViolations;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

import com.myorg.is.entity.dto.request.InventoryPatchRequest;
import com.myorg.is.entity.dto.request.InventoryRequest;
import com.myorg.is.entity.dto.request.QuantityReduceRequest;
import com.myorg.is.entity.dto.response.inventory.InventoryResponse;
import com.myorg.is.entity.dto.validation.Violation;
import com.myorg.is.exception.BatchRequestValidationException;
import com.myorg.is.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = INVENTORY_BASE_URI)
@RequiredArgsConstructor
@Validated
@Slf4j
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping("/skuCodes")
  public ResponseEntity<List<InventoryResponse>> isInStock(
      @RequestParam("skuCodes") List<@NotBlank(message = "{inventory.controller.requestParams.skuCode.isBlank}") String> skuCodes) {
    return ResponseEntity.ok(inventoryService.findInventoryRecordsBySkuCodes(skuCodes));
  }

  @PostMapping
  public ResponseEntity<InventoryResponse> saveInventoryRecord(
      @Valid @RequestBody InventoryRequest inventoryRequest,
      HttpServletRequest httpServletRequest) {

    log.info("saveInventoryRecord invoked with inventory request payload: {}", inventoryRequest);

    InventoryResponse inventoryResponse = inventoryService.saveInventoryRecord(inventoryRequest);
    return created(create(httpServletRequest.getRequestURI() + "/" + inventoryResponse.id()))
        .body(inventoryResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryResponse> getInventoryRecordById(
      @Valid
      @UUID(message = "{inventory.controller.pathVariable.id.uuid}")
      @PathVariable String id
  ) {
    log.info("getInventoryRecordById invoked with path variable id: {}", id);
    return ok(inventoryService.findInventoryRecordById(id));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<InventoryResponse> patchInventoryRecord(
      @Valid
      @UUID(message = "{inventory.controller.pathVariable.id.uuid}")
      @PathVariable String id,
      @Valid @RequestBody InventoryPatchRequest inventoryPatchRequest
  ) {
    log.info(
        "patchInventoryRecord invoked with path variable id: {} and inventory patch request payload: {}",
        id, inventoryPatchRequest);
    return ok(inventoryService.patchInventoryRecord(id, inventoryPatchRequest));
  }

  @GetMapping("skuCodes/{skuCode}")
  public ResponseEntity<InventoryResponse> getInventoryRecordBySkuCode(
      @Valid
      @NotBlank(message = "{inventory.controller.pathVariable.skuCode.isBlank}")
      @PathVariable("skuCode") String skuCode) {
    log.info("getInventoryRecordBySkuCode invoked with path variable skuCode: {}", skuCode);

    return ok(inventoryService.findInventoryRecordBySkuCode(skuCode));
  }

  @PutMapping("/skuCodes/reduceQuantities")
  public ResponseEntity<List<InventoryResponse>> reduceQuantitiesBySkuCode(
      @RequestBody List<QuantityReduceRequest> quantityReduceRequests) {
    log.info("reduceQuantitiesBySkuCode invoked with request payload: {}", quantityReduceRequests);

    LinkedHashMap<Integer, List<Violation>> violations = getQuantityReduceBatchRequestViolations(
        quantityReduceRequests);

    if (!violations.isEmpty()) {
      throw new BatchRequestValidationException("Invalid batch request payload", violations);
    }

    // TODO: Address the scenario where the skuCode is duplicated in the request payload items.

    return ok(inventoryService.reduceQuantitiesBySkuCodes(quantityReduceRequests));
  }
}
