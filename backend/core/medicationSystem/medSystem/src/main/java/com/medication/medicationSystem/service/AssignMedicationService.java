package com.medication.medicationSystem.service;

import java.util.List;

import com.medication.medicationSystem.model.AssignMedication;
import com.medication.medicationSystem.repository.MedicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class AssignMedicationService {
    @Autowired 
    MedicationRepository medicationRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<AssignMedication> getAll() {
        
        return medicationRepo.findAll();
    }

    // this is for find nearby store
    public List<Double> getLocation(String patId){
        List<Double> coordinates = medicationRepo.findLocationByPatId(patId);
        return coordinates;
    }

    public AssignMedication createDoc(AssignMedication document) {
        
        String doc_username = authService.getCurrentUser().getUsername();
        document.setDocId(doc_username);
        return medicationRepo.save(document);
    }


}
