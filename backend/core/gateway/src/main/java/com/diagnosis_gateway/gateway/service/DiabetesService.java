package com.diagnosis_gateway.gateway.service;

import java.util.List;
import java.util.Optional;

import com.diagnosis_gateway.gateway.Repository.DiabetesRepository;
import com.diagnosis_gateway.gateway.model.Diabetes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DiabetesService {
    
    @Autowired
    private AuthService authService;
    @Autowired
    private DiabetesRepository diabetesRepo;

    public String save(Diabetes diabetes){
        String username = authService.getCurrentUser().getUsername();

        diabetes.setUsername(username);
        diabetesRepo.save(diabetes);

        return "Saved";
        


    }
    public String fetchAndSaveOutcomes(Integer id, Integer outcome, Double probability){
        Diabetes diabetes = diabetesRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("Record Not Found"));
        diabetes.setOutcome(outcome);
        diabetes.setProbability(probability);
        diabetesRepo.save(diabetes);
        return "Data Saved in db";
    }

    public List<Diabetes> getPatientDiabetesReport(){
        String username = authService.getCurrentUser().getUsername();
        List<Diabetes> diabtesRecords = diabetesRepo.findByUsername(username);  
        return diabtesRecords;

    }
    public String saveByDoc(Diabetes diabetes) {
        diabetesRepo.save(diabetes);
        return "Saved Successfully";
    }
}
