package com.myorg.ps.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class Constants {

  public static final String PRODUCT_BASE_URI = "/api/products";
  public static final String MONGODB_IMAGE = "mongo:5.0";
}
