package com.myorg.os.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  /**
   * WebClient is used to make HTTP calls to other services. This enables synchronous communication
   * between the services
   *
   * @return WebClient
   */
  @Bean(name = "webClient")
  public WebClient webClient() {
    return WebClient.builder().build();
  }
}
