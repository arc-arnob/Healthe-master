package com.medication.medicationSystem.controller;

import java.util.List;

import com.medication.medicationSystem.model.AssignMedication;
import com.medication.medicationSystem.service.AssignMedicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicationController {

    @Autowired
    private AssignMedicationService assignMedicationService;


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

    @GetMapping("/nearByStore")
    public 

}
