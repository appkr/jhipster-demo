package dev.appkr.demo1.config;

import dev.appkr.demo2.java.ApiClient;
import dev.appkr.demo2.java.api.Demo2Api;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Demo2ApiConfiguration {

  private final ApplicationProperties properties;

  public Demo2ApiConfiguration(ApplicationProperties properties) {
    this.properties = properties;
  }

  @Bean
  public RestTemplate demo2ApiRestTemplate() {
    return new RestTemplateBuilder()
        .rootUri(properties.getDemo2Api().getHost())
        .build();
  }

  @Bean
  public ApiClient demo2ApiClient() {
    ApiClient apiClient = new ApiClient(demo2ApiRestTemplate());
    apiClient.setApiKeyPrefix("bearer");
    apiClient.setApiKey("Fill this");
    return apiClient;
  }

  @Bean
  public Demo2Api demo2Api() {
    return new Demo2Api(demo2ApiClient());
  }
}
