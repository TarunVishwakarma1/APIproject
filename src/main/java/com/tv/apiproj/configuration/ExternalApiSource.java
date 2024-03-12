package com.tv.apiproj.configuration;

import com.tv.apiproj.dao.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ExternalApiSource {


    private final RestTemplate restTemplate;
    @Autowired
    public ExternalApiSource (RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public Object send(String url){
        try{
            return restTemplate.getForEntity(url, Object.class).getBody();
        }catch (HttpClientErrorException ex){
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Handle 404 Not Found response
                throw new CustomException("Resource not found", HttpStatus.NOT_FOUND.value());
            } else {
                // Handle other client errors
                throw ex;
            }
        }
    }
}
