package com.diagnosis_gateway.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        System.out.println("Here in diagnosis gateway");
        return new RestTemplate();
    }

}
