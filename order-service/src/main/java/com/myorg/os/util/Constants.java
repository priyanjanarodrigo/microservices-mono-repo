package com.myorg.os.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

  public static final String ORDER_BASE_URI = "/api/orders";
  public static final String MYSQL_DB_IMAGE = "mysql:8.0";
}
