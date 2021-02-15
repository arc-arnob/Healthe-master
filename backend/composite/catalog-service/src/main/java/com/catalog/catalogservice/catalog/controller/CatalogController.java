package com.catalog.catalogservice.catalog.controller;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/patient-profile") //<-- product-composite
    public Object showPatientProfile(@RequestHeader(value = "Authorization") String token){

        System.out.println("Here in catalog service 1");
        System.out.println("autho header: " + token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entityReq = new HttpEntity<String>("request", headers);

         //need to forward auth header
         ResponseEntity<Object> responseEntity = restTemplate.exchange("http://zuul-service/appointment/patient/profile",HttpMethod.GET,entityReq, Object.class);
         Object patient = responseEntity.getBody();
         return patient;
     
    }
}
