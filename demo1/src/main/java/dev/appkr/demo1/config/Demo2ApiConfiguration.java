package dev.appkr.demo1.config;

import dev.appkr.demo2.java.ApiClient;
import dev.appkr.demo2.java.api.Demo2Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

@Configuration
public class Demo2ApiConfiguration {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Bean
  @ConfigurationProperties("security.oauth2.client")
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    return new ClientCredentialsResourceDetails();
  }

  @Bean
  public RestTemplate restTemplateForDemo2Api() {
    RestTemplate restTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails());
    restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
          final ClientHttpResponse response = execution.execute(request, body);

          log.info("REQUEST & RESPONSE TO DEMO2 {} {}",
              new HashMap<String, Object>() {{
                put("endpoint", request.getMethod() + " " + request.getURI());
                put("headers", request.getHeaders());
                put("body", new String(body));
              }},
              new HashMap<String, Object>() {{
                put("status", response.getStatusCode());
                put("headers", response.getHeaders());
                put("body", response.getBody());
              }}
          );

          return response;
        }
    ));

    return restTemplate;
  }

    @Bean
  public Demo2Api demo2Api() {
    return new Demo2Api(new ApiClient(restTemplateForDemo2Api()));
  }
}
