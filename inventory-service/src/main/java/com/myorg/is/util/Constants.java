package com.myorg.is.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

  public static final String INVENTORY_BASE_URI = "/api/inventories";
  public static final String POSTGRES_DB_IMAGE = "postgres:15";
}
