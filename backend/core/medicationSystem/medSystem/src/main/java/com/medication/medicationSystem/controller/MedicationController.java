package com.medication.medicationSystem.controller;

import java.util.List;

import com.medication.medicationSystem.model.AssignMedication;
import com.medication.medicationSystem.service.AssignMedicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/medication")
public class MedicationController {

    @Autowired
    private AssignMedicationService assignMedicationService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancer;


    @PostMapping("/create") //onyl doctor
    public String createDoc(@RequestBody AssignMedication assignMedication){
        System.out.println(assignMedication.getDocId());
        assignMedicationService.createDoc(assignMedication);
        return "Saved";
    }
    @GetMapping("/get")
    public List<AssignMedication> getMedication(){
        return assignMedicationService.getAll();
    }

    @GetMapping("/nearByStore") //getForEntity
    public String getNearByStores(){
        List<Double> coordinates = assignMedicationService.getMedicationStore();
        System.out.println(coordinates+"it this empty?");
        Double latitude = coordinates.get(0);
        Double longitude = coordinates.get(1);

        String uri = loadBalancer.choose("store-locator").getServiceId();
        String url = "http://"+uri.toString() + "/booking/near/"+latitude+","+longitude;
        ResponseEntity<String> res = restTemplate.getForEntity(url,String.class); // error
        return res.getBody();

    }
}