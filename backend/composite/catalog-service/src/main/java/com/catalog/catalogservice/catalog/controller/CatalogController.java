package com.catalog.catalogservice.catalog.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @Autowired
    LoadBalancerClient loadBalancer;



    // Patient Controllers

    @GetMapping("/patient-profile") //<-- product-composite
    public Object showPatientProfile(@RequestHeader(value = "Authorization") String token){

        System.out.println("Here in catalog service 1");
        System.out.println("autho header: " + token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entityReq = new HttpEntity<String>("request", headers);

        String uri = loadBalancer.choose("app-service").getServiceId();

        System.out.println(uri);
        //http://localhost:8084/patient/profile
        String url = "http://"+uri.toString() + "/patient/profile";
        System.out.println(url);

         //need to forward auth header--fixed
         ResponseEntity<Object> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entityReq, Object.class);
         Object patient = responseEntity.getBody();
         return patient;
     
    }

    @PostMapping("/patient-register")
    public String registerPatient(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/patient/register";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }

    
    @PostMapping("/patient-app-booking")
    public String patientAppointmentBooking(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/patient/appointmentbooking";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }


    // DOCTOR CONTROLLERSS


    @PostMapping("/doctor-register")
    public String registerDoctor(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/register";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }

    @PutMapping("/doctor-update")
    public String updateDoctor(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/update/profile";

        restTemplate
                .put(url, entity, String.class);

        return "Updated";
    }

    @DeleteMapping("/doctor-delete")
    public String deleteDoctor(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/delete/profile";

        ResponseEntity<String> status = restTemplate.exchange(url,HttpMethod.DELETE, entity, String.class);

        return status.getBody();
    }




    // Appointment Controllers
        // get available date and time for appointment
        // Look at all the appoiments made by a patient-details
        // Doctor can look at his schedule

    // Forum Controllers

    
}
