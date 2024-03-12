package com.tv.apiproj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationClass {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
