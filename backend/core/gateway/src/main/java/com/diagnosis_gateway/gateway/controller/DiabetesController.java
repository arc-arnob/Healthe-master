package com.diagnosis_gateway.gateway.controller;

import java.util.List;

import com.diagnosis_gateway.gateway.dto.DiabetesPYDto;
import com.diagnosis_gateway.gateway.dto.StrokeDto;
import com.diagnosis_gateway.gateway.model.Diabetes;
import com.diagnosis_gateway.gateway.model.Stroke;
import com.diagnosis_gateway.gateway.service.DiabetesService;
import com.diagnosis_gateway.gateway.service.StrokesService;
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
    private StrokesService strokeService;

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

    @PostMapping("patient/getStrokeDiagnosis/")
    public Double[] saveAndGetDiagnosis(@RequestBody Stroke body){

        String status = strokeService.save(body);

        StrokeDto dto = new StrokeDto();
        dto.setGender(body.getGender());
        dto.setEver_married(body.getEver_married());
        dto.setWork_type(body.getWork_type()); 
        dto.setResidence_type(body.getResidence_type());
        dto.setSmoking_status(body.getSmoking_status());
        dto.setAge(body.getAge());
        dto.setHypertension(body.getHypertension());
        dto.setHeart_disease(body.getHeart_disease());
        dto.setAvg_glucose_level(body.getAvg_glucose_level());
        dto.setBmi(body.getBmi());

        System.out.println(body.getGender());
        System.out.println(body.getEver_married());
        System.out.println(body.getWork_type());
        System.out.println(body.getResidence_type());
        System.out.println(body.getSmoking_status());
        System.out.println(body.getAge());
        System.out.println(body.getHypertension());
        System.out.println(body.getHeart_disease());
        System.out.println(body.getAvg_glucose_level());
        System.out.println(body.getBmi());

        



        String uri = loadBalancer.choose("stroke-app").getServiceId();
        String url = "http://"+uri.toString() + "/strokeapi/status/";
        System.out.println(url);
        ResponseEntity<Double[]> responseEntity = restTemplate.postForEntity(url,dto,Double[].class);
        Double[] patient = responseEntity.getBody();
        return patient;
    }


}
