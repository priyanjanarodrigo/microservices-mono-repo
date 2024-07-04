package com.myorg.ps.integration;

import static com.myorg.ps.util.Constants.PRODUCT_BASE_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.ps.entity.dto.request.product.ProductRequest;
import com.myorg.ps.entity.dto.response.product.ProductResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
class ProductIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProductRepository productRepository;

  @Test
  @DisplayName("Product should be persisted to the database and should be able retrieve it back")
  void testCreateProductAndRetrieve() throws Exception {
    ProductRequest productRequest = ProductRequest.builder()
        .name("Apple iPhone 14 Por Max 256GB")
        .description("Flagship device launched by apple in 2022")
        .price(BigDecimal.valueOf(450000))
        .skuCode("aip14pmx256")
        .build();

    MvcResult postMvcResult = mockMvc.perform(post(PRODUCT_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(productRequest.name()))
        .andExpect(jsonPath("$.description").value(productRequest.description()))
        .andExpect(jsonPath("$.price").value(productRequest.price()))
        .andExpect(jsonPath("$.skuCode").value(productRequest.skuCode()))
        .andExpect(header().exists(LOCATION))
        .andReturn();

    ProductResponse productResponse = objectMapper.readValue(
        postMvcResult.getResponse().getContentAsString(), ProductResponse.class);

    final String expectedLocationHeaderValue = PRODUCT_BASE_URI + "/" + productResponse.id();
    assertEquals(expectedLocationHeaderValue, postMvcResult.getResponse().getHeaderValue(LOCATION),
        "Expected LOCATION header value should be returned");

    mockMvc.perform(get(expectedLocationHeaderValue))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(productResponse.id()))
        .andExpect(jsonPath("$.name").value(productResponse.name()))
        .andExpect(jsonPath("$.description").value(productResponse.description()))
        .andExpect(jsonPath("$.price").value(productResponse.price()))
        .andExpect(jsonPath("$.skuCode").value(productResponse.skuCode()))
        .andReturn();
  }
}
