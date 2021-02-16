package com.catalog.catalogservice.catalog.controller;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    
    @Autowired
    RestTemplate restTemplate;

    // Perform Load Balancing here.




    // Patient Controllers

    @GetMapping("/patient-profile") //<-- product-composite
    public Object showPatientProfile(@RequestHeader(value = "Authorization") String token){

        System.out.println("Here in catalog service 1");
        System.out.println("autho header: " + token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entityReq = new HttpEntity<String>("request", headers);

         //need to forward auth header--fixed
         ResponseEntity<Object> responseEntity = restTemplate.exchange("http://zuul-service/appointment/patient/profile",HttpMethod.GET,entityReq, Object.class);
         Object patient = responseEntity.getBody();
         return patient;
     
    }

    @PostMapping("/patient-register")
    public String registerPatient(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        ResponseEntity<String> result = restTemplate
                .postForEntity("http://zuul-service/appointment/patient/register", entity, String.class);

        return "Patient Register Successfully!";
    }

    @PostMapping("/patient-app-booking")
    public String patientAppointmentBooking(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        ResponseEntity<String> result = restTemplate
                .postForEntity("http://zuul-service/appointment/patient/appointmentbooking", entity, String.class);

        return result.getBody();
    }

    @PostMapping("/doctor-register")
    public String registerDoctor(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        ResponseEntity<String> result = restTemplate
                .postForEntity("http://zuul-service/appointment/doctor/register", entity, String.class);

        return "Doctor Register Successfully!";
    }

    // Appointment Controllers
    // Forum Controllers

    
}
