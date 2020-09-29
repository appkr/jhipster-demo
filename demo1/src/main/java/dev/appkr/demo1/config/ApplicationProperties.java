package dev.appkr.demo1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Demo 1.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  public Demo2Api demo2Api = new Demo2Api();

  public Demo2Api getDemo2Api() {
    return demo2Api;
  }

  public static class Demo2Api {
    private String host = "http://localhost:8082";

    public String getHost() {
      return host;
    }
  }
}
