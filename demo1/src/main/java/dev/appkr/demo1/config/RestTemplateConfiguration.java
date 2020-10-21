package dev.appkr.demo1.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfiguration {
    @Bean
    @Qualifier("restTemplateForDemo2Api")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
