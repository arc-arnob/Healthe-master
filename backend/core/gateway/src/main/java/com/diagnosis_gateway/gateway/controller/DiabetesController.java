package com.diagnosis_gateway.gateway.controller;

import java.util.List;

import com.diagnosis_gateway.gateway.dto.DiabetesPYDto;
import com.diagnosis_gateway.gateway.model.Diabetes;
import com.diagnosis_gateway.gateway.service.DiabetesService;
import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/diagnosis")
public class DiabetesController {
    @Autowired
    private DiabetesService diabetesService;

    @Autowired
    LoadBalancerClient loadBalancer;
    @Autowired
    RestTemplate restTemplate;



    @PostMapping("/patient/getdiagnosis")
    public Object saveDiagnosis(@RequestBody Diabetes diabetes){

        String status = diabetesService.save(diabetes);

        // maptoDto.
        DiabetesPYDto dto = new DiabetesPYDto();
        dto.setGlucose(diabetes.getGlucose());
        dto.setAge(diabetes.getAge());
        dto.setBmi(diabetes.getBMI());
        dto.setInsulin(diabetes.getInsulin());

        System.out.println(diabetes.getGlucose());
        System.out.println(diabetes.getAge());
        System.out.println(diabetes.getBMI());
        System.out.println(diabetes.getInsulin());


        String uri = loadBalancer.choose("diabetes-app").getServiceId();
        System.out.println(loadBalancer.choose("diabetes-app").getHost());
        System.out.println(loadBalancer.choose("diabetes-app").getPort());
        String url = "http://"+uri.toString() + "/diabetesapi/status/";
        System.out.println(url);
        ResponseEntity<Integer[]> responseEntity = restTemplate.postForEntity(url,dto,Integer[].class);
        Integer[] patient = responseEntity.getBody();
        return patient;

    }


}
