package com.myorg.ag.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagementConfiguration {

  @Bean(name = "httpTraceRepository")
  public HttpExchangeRepository httpTraceRepository() {
    return new InMemoryHttpExchangeRepository();
  }
}
